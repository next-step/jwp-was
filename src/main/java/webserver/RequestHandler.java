package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.requestline.old.QueryStrings;
import http.request.headers.Headers;
import http.request.requestline.old.RequestLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            String url = RequestLine.of(br).getStringPath();
            Headers headers = new Headers(br);

            if (url.startsWith("/users")) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.getValue("Content-Length")));
                Map<String, String> map = QueryStrings.parseQueryStrings(requestBody);
                User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                DataBase.addUser(user);
                logger.debug("user: {}", user);
                url = "/index.html";
                DataOutputStream dos = new DataOutputStream(out);
                logger.debug("url: {}", url);
                byte[] body = FileIoUtils.loadFileFromClasspath(url);
                response302Header(dos, url);
                responseBody(dos, body);
            } else if (url.equals("/login")) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.getValue("Content-Length")));
                Map<String, String> map = QueryStrings.parseQueryStrings(requestBody);
                User userById = DataBase.findUserById(map.get("userId"));
                if (userById.getPassword().equals(map.get("password"))) {
                    DataOutputStream dos = new DataOutputStream(out);
                    url = "/index.html";
                    byte[] body = FileIoUtils.loadFileFromClasspath(url);
                    response200HeaderWithSetCookie(dos, url, "logined=true; Path=/");
                    responseBody(dos, body);
                } else {
                    DataOutputStream dos = new DataOutputStream(out);
                    url = "/user/login_failed.html";
                    byte[] body = FileIoUtils.loadFileFromClasspath(url);
                    response302HeaderWithSetCookie(dos, url, "logined=false");
                    responseBody(dos, body);
                }

            } else if ("/user/list".equals(url)) {
                DataOutputStream dos = new DataOutputStream(out);
                String cookie = headers.getValue("Cookie");
                if (cookie.contains("logined=true")) {
                    Collection<User> users = DataBase.findAll();
                    url = "/user/list.html";

                    TemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/templates");
                    loader.setSuffix(".html");
                    Handlebars handlebars = new Handlebars(loader);
                    Template template = handlebars.compile("user/list");
                    List<User> allUsers = users.stream()
                            .collect(Collectors.toList());
                    Map<String, List<User>> map = new HashMap<>();
                    map.put("users", allUsers);
                    String profile = template.apply(map);
                    byte[] body = profile.getBytes();
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                } else {
                    url = "/user/login.html";
                    byte[] body = FileIoUtils.loadFileFromClasspath(url);
                    response302Header(dos, url);
                    responseBody(dos, body);
                }
            } else if (url.endsWith(".css")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath(url);
                response200HeaderForCss(dos, body.length);
                responseBody(dos, body);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath(url);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithSetCookie(DataOutputStream dos, String url, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200HeaderWithSetCookie(DataOutputStream dos, String url, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200HeaderForCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}