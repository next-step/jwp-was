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
import webserver.http.BufferedReaderToHttpRequest;
import webserver.http.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);
			HttpRequest httpRequest = bufferedReaderToHttpRequest.parsed();
            DataOutputStream dos = new DataOutputStream(out);

			String path = httpRequest.getPath();
            logger.debug("path : {}", path);

            HttpMethod method = httpRequest.getMethod();
            logger.debug("method : {}", method);

            if (path.endsWith(".html")) {
                processPageRequest(dos, path);
            } else if (containsStaticPath(path)) {
                processStaticRequest(dos, path);
            } else if (path.startsWith("/user/create")) {
                join(httpRequest, dos);
            } else if (path.startsWith("/user/login")) {
                login(httpRequest, dos);
            } else if (path.startsWith("/user/list")) {
                list(httpRequest, dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void processPageRequest(DataOutputStream dos, String path) throws IOException, URISyntaxException {
        path = "./templates" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private boolean containsStaticPath(String path) {
        return path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/images");
    }

    private void processStaticRequest(DataOutputStream dos, String path) throws IOException, URISyntaxException {
        path = "./static" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        if (path.startsWith("./static/css")) {
            response200CssHeader(dos, body.length);
        } else {
            response200Header(dos, body.length);
        }
        responseBody(dos, body);
    }

    private void login(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        User loginUser = DataBase.findUserById(httpRequest.getParameter("userId"));
        logger.debug("로그인 사용자: {}", loginUser);

        boolean logined = false;
        String location = "/user/login_failed.html";

        if (loginUser != null && loginUser.getPassword().equals(httpRequest.getParameter("password"))) {
            logger.debug("로그인 성공: {}", loginUser);
            logined = true;
            location = "/index.html";
        }
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void join(HttpRequest request, DataOutputStream dos) {
        User newUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        logger.debug("회원가입 사용자 : {}", newUser);
        DataBase.addUser(newUser);

        response302Header(dos);
    }

    private void list(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

		if (httpRequest.getCookie().get("logined").equals("true")) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/profile");

            User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
            String profilePage = template.apply(user);
            logger.debug("ProfilePage : {}", profilePage);

            body = profilePage.getBytes();
        }
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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

    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
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
}
