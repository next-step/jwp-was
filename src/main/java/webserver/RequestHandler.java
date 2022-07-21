package webserver;

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

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            RequestLine requestLine = new RequestLine(URLDecoder.decode(reader.readLine(), UTF_8));
            HttpHeaders headers = HttpHeaders.from(reader);

            String requestBody = URLDecoder.decode(IOUtils.readData(reader, headers.getContentLength()), UTF_8);
            logger.debug("request body = {}", requestBody);
            DataOutputStream dos = new DataOutputStream(out);
            response(requestLine, headers, requestBody, dos);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(RequestLine requestLine, HttpHeaders headers, String requestBody, DataOutputStream dos) throws IOException, URISyntaxException {
        String path = requestLine.getRequestPath();
        HttpMethod method = requestLine.getMethod();
        String cookie = headers.get("Cookie");
        byte[] body = new byte[0];

        if (method.isGet() && ("/".equals(path) || "/index.html".equals(path))) {
            body = response200WithView(dos, "index");
        }

        if (method.isGet() && "/user/form.html".equals(path)) {
            body = response200WithView(dos,"user/form");
        }

        if (method.isGet() && "/user/login.html".equals(path)) {
            body = response200WithView(dos, "user/login");
        }

        if (method.isGet() && "/user/list".equals(path)) {
            body = showUsers(dos, cookie);
        }

        if (method.isPost() && "/user/create".equals(path)) {
            createUser(requestBody, dos);
        }

        if (method.isPost() && "/user/login".equals(path)) {
            body = login(requestBody, dos);
        }

        responseBody(dos, body);
    }

    private byte[] showUsers(DataOutputStream dos, String cookie) throws IOException, URISyntaxException {
        if (cookie != null && cookie.contains("logined=true")) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");

            Collection<User> users = DataBase.findAll();
            String userListPage = template.apply(users);

            byte[] body = userListPage.getBytes(UTF_8);
            response200Header(dos, body.length);
            return body;
        }
        return response200WithView(dos, "user/login");
    }

    private byte[] response200WithView(DataOutputStream dos, String viewName) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(viewResolver(viewName));
        response200Header(dos, body.length);
        return body;
    }

    private String viewResolver(String viewName) {
        return String.format("./templates/%s.html", viewName);
    }

    private void createUser(String requestBody, DataOutputStream dos) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        logger.debug("userId = {}", userId);
        logger.debug("password = {}", password);
        logger.debug("name = {}", name);
        logger.debug("email = {}", email);

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        response302Header(dos);
    }

    private byte[] login(String requestBody, DataOutputStream dos) throws IOException, URISyntaxException {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (user != null && password.equals(user.getPassword())) {
            response302HeaderWithLoginSuccessCookie(dos);
            return FileIoUtils.loadFileFromClasspath(viewResolver("index"));
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(viewResolver("user/login_failed"));
        response200HeaderWithLoginFailedCookie(dos, body.length);
        return body;
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

    private void response200HeaderWithLoginFailedCookie(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: logined=false\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithLoginSuccessCookie(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Set-Cookie: logined=true; Path=/\r\n");
            dos.writeBytes("Location: /index.html\r\n");
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
