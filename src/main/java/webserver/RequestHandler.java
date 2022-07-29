package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug("requst line : {}", line);
            if (line == null) {
                return;
            }
            // Request Line
            RequestLineParser RLParser = new RequestLineParser(line);
            String url = RLParser.getUri().getPath();
            // Request Header
            int contentLength = 0;
            boolean logined = false;
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("header: {}", line);
                if (line.contains("Content-Length")){
                    contentLength = getContentLength(line);
                }
                if (line.contains("Cookie")) {
                    logined = isLogin(line);
                }
            }

            if (url.startsWith("/user/create")) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = new User(params.get("userId"), params.get("password"),params.get("name"),params.get("email"));
                DataBase.addUser(user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            } else if ("/user/login".equals(url)) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = DataBase.findUserById(params.get("userId"));
                DataOutputStream dos = new DataOutputStream(out);
                if (user != null) {
                    if (user.getPassword().equals(params.get("password"))) {
                        response302LoginHeader(dos, "logined=true", "/index.html");
                    } else {
                        response302LoginHeader(dos,"logined=false","/user/login_failed.html");
                    }
                } else {
                    response302LoginHeader(dos,"logined=false","/user/login_failed.html");
                }
            } else if ("/user/list".equals(url)) {
                if (!logined) {
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
                Collection<User> users = DataBase.findAll();
                String userList = HandleBarTemplateLoader.load("user/list",users);
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = userList.getBytes(StandardCharsets.UTF_8);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if (url.endsWith(".css")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "css");
                responseBody(dos, body);
            } else if (url.endsWith(".js")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "js");
                responseBody(dos, body);
            } else if (url.endsWith(".ttf")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "fonts");
                responseBody(dos, body);
            } else if (url.endsWith(".woff")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "fonts");
                responseBody(dos, body);
            } else if (url.endsWith(".svg")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "fonts");
                responseBody(dos, body);
            }else if (url.endsWith(".png")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + url);
                response200StaticHeader(dos, body.length, "images");
                responseBody(dos, body);
            } else if (url.endsWith(".ico")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + url);
                response200StaticHeader(dos, body.length, "images");
                responseBody(dos, body);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + url);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isLogin(String line) {
        String cookieString = line.split(":")[1].trim();
        if (cookieString.isEmpty()) {
            return false;
        }
        Map<String, String> cookies = new HashMap<>();
        for (String cookie : cookieString.split(";")) {
            String cookieName = cookie.split("=")[0];
            String cookieValue = cookie.split("=")[1];
            cookies.put(cookieName, cookieValue);
        }
        String loginedValue = cookies.get("logined");
        if (loginedValue == null) {
            return false;
        }
        return Boolean.parseBoolean(loginedValue);
    }

    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302LoginHeader(DataOutputStream dos, String cookie, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200StaticHeader(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + type + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
