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
import webserver.http.request.HttpRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            HttpRequest httpRequest = HttpRequest.of(br);
            byte[] body = {};
            boolean isLogin = false;

            if (httpRequest.isLogin()) {
                isLogin = true;
            }

            if (httpRequest.getPath().equals("/user/list")) {
                DataOutputStream dos = new DataOutputStream(out);
                if (isLogin) {
                    Template template = handlebars.compile("user/list");
                    Collection<User> users = DataBase.findAll();
                    body = template.apply(Map.of("users", users)).getBytes();
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                    return;
                }
                responseIsNotLoginHeader(dos);
                return;
            }

            if (httpRequest.getPath().equals("/user/create")) {
                User user = new User(
                        httpRequest.getParam("userId"),
                        httpRequest.getParam("password"),
                        httpRequest.getParam("name"),
                        httpRequest.getParam("email")
                );
                DataBase.addUser(user);
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos);
                responseBody(dos, body);
                return;
            }

            if (httpRequest.getPath().equals("/user/login")) {
                User user = DataBase.findUserById(httpRequest.getParam("userId"));
                DataOutputStream dos = new DataOutputStream(out);

                if (user == null) {
                    responseLoginFailHeader(dos);
                    return;
                }

                if (user.isPasswordCorrect(httpRequest.getParam("password"))) {
                    response302LoginSuccessHeader(dos);
                    return;
                }
                responseLoginFailHeader(dos);
                return;
            }

            if (httpRequest.isFilePath()) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath());
            }
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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

    private void response200LoginFailHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: logined=false; Path=/ \r\n");
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

    private void response302LoginSuccessHeader(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=true; Path=/ \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseLoginFailHeader(DataOutputStream dos) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
            response200LoginFailHeader(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseIsNotLoginHeader(DataOutputStream dos) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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
