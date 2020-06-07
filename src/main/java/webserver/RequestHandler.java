package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.*;
import model.User;
import model.Users;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HandlebarsHelper;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();
            logger.debug("Request Line :: {}", line);
            RequestLine requestLine = RequestLineParser.parse(line);

            List<String> headers = new ArrayList<>();

            line = br.readLine();
            while (!line.equals("")) {
                logger.debug("Header :: {}", line);
                headers.add(line);
                line = br.readLine();
            }

            Header header = new Header(headers.toArray(new String[headers.size()]));
            Cookie cookie = new Cookie(header.getValue("Cookie"));

            String contentLengthStr = header.getValue("Content-Length");
            int contentLength = 0;
            if (!Strings.isBlank(contentLengthStr)) {
                contentLength = Integer.parseInt(contentLengthStr);
            }

            String requestBody = IOUtils.readData(br, contentLength);
            logger.debug("Body :: {}", requestBody);

            HttpMethod httpMethod = requestLine.getHttpMethod();
            String path = requestLine.getPath();

            if (isPost(httpMethod) && "/user/create".equals(path)) {
                FormData formData = new FormData(requestBody);
                String userId = formData.getValue("userId");
                String password = formData.getValue("password");
                String name = formData.getValue("name");
                String email = formData.getValue("email");

                DataBase.addUser(new User(userId, password, name, email));
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");

            } else if (isPost(httpMethod) && "/user/login".equals(path)) {
                FormData formData = new FormData(requestBody);
                String userId = formData.getValue("userId");
                String password = formData.getValue("password");

                User user = DataBase.findUserById(userId);

                if (isSamePassword(user.getPassword(), password)) {
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderWithCookies(dos, "/index.html", "logined", "true");
                } else {
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderWithCookies(dos, "/user/login_failed.html", "logined", "false");
                }

            } else if (isGet(httpMethod) && "/user/list".equals(path)) {
                if (isLogined(cookie)) {
                    Users users = new Users(DataBase.findAll());

                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");

                    Handlebars handlebars = new Handlebars(loader);
                    handlebars.registerHelpers(new HandlebarsHelper());

                    Template template = handlebars.compile("user/list");

                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = template.apply(users).getBytes();
                    response200HtmlHeader(dos, body.length);
                    responseBody(dos, body);
                } else {
                    DataOutputStream dos = new DataOutputStream(out);
                    response302Header(dos, "/index.html");
                }

            } else {
                if (path.endsWith(".css")) {
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = FileIoUtils.loadFileFromClasspath("./static/" + path);
                    response200StylesheetHeader(dos, body.length);
                    responseBody(dos, body);
                } else {
                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + path);
                    response200HtmlHeader(dos, body.length);
                    responseBody(dos, body);
                }
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isSamePassword(String password1, String password2) {
        if (password1 == null) {
            return false;
        }
        return password1.equals(password2);
    }

    private boolean isLogined(Cookie cookie) {
        String logined = cookie.getValue("logined");
        if ("true".equals(logined)) {
           return true;
        }
        return false;
    }

    private boolean isGet(HttpMethod httpMethod) {
        return HttpMethod.GET.equals(httpMethod);
    }

    private boolean isPost(HttpMethod httpMethod) {
        return HttpMethod.POST.equals(httpMethod);
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200HtmlHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200StylesheetHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithCookies(DataOutputStream dos, String location, String cookieName, String cookieValue) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookieName + "=" + cookieValue + "; Path=/\r\n");
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
}
