package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                    contentLength = HttpHeaderParser.getContentLength(line);
                }
                if (line.contains("Cookie")) {
                    logined = HttpHeaderParser.isLogin(line);
                }
            }

            if (url.startsWith("/user/create")) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = new User(params.get("userId"), params.get("password"),params.get("name"),params.get("email"));
                DataBase.addUser(user);
                DataOutputStream dos = new DataOutputStream(out);
                HttpResponseWriter.response302Header(dos, "/index.html");
            } else if ("/user/login".equals(url)) {
                String body = IOUtils.readData(br, contentLength);
                QueryStringParser QSParser = new QueryStringParser(body);
                Map<String, String> params = QSParser.getQueryParameters();
                User user = DataBase.findUserById(params.get("userId"));
                DataOutputStream dos = new DataOutputStream(out);
                if (user != null) {
                    if (user.getPassword().equals(params.get("password"))) {
                        HttpResponseWriter.response302CookieHeader(dos, "logined=true", "/index.html");
                    } else {
                        HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
                    }
                } else {
                    HttpResponseWriter.response302CookieHeader(dos,"logined=false","/user/login_failed.html");
                }
            } else if ("/user/list".equals(url)) {
                if (!logined) {
                    HttpResponseWriter.responseResource(out, "./templates/user/login.html");
                }
                Collection<User> users = DataBase.findAll();
                String userList = HandleBarTemplateLoader.load("user/list", users);
                HttpResponseWriter.responseUserListResource(out, userList);
            } else if (url.endsWith(".css")) {
                HttpResponseWriter.responseStaticResorce(out, url, "css");
            } else if (url.endsWith(".js")) {
                HttpResponseWriter.responseStaticResorce(out, url, "js");
            } else if (url.endsWith(".ttf")) {
                HttpResponseWriter.responseStaticResorce(out, url, "fonts");
            } else if (url.endsWith(".woff")) {
                HttpResponseWriter.responseStaticResorce(out, url, "fonts");
            } else if (url.endsWith(".svg")) {
                HttpResponseWriter.responseStaticResorce(out, url, "fonts");
            }else if (url.endsWith(".png")) {
                HttpResponseWriter.responseStaticResorce(out, url, "images");
            } else if (url.endsWith(".ico")) {
                HttpResponseWriter.responseStaticResorce(out, url, "images");
            } else {
                HttpResponseWriter.responseResource(out, url);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
