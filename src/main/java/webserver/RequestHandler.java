package webserver;

import com.google.common.base.Charsets;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.request.Method;
import webserver.http.request.Request;
import webserver.http.request.RequestReader;
import webserver.http.request.exception.NullRequestException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestReader requestReader;

    public RequestHandler(Socket connectionSocket, RequestReader requestReader) {
        this.connection = connectionSocket;
        this.requestReader = requestReader;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
        ) {
            Request request = requestReader.read(bufferedReader);
            logger.info("[request] = {}", request);

            if (request.hasMethod(Method.GET) && request.hasPath("/")) {
                try {
                    String resource = "/index.html";
                    byte[] bytes = FileIoUtils.loadFileFromClasspath("./static" + resource);
                    response200Header(dos, bytes.length, findContentType(resource));
                    responseBody(dos, bytes);
                    return;
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }


            if (request.hasMethod(Method.GET) && isResource(request)) {
                try {
                    String resource = request.getPath();
                    byte[] bytes = FileIoUtils.loadFileFromClasspath("./static" + resource);
                    response200Header(dos, bytes.length, findContentType(resource));
                    responseBody(dos, bytes);
                    return;
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            if (request.hasMethod(Method.POST) && request.hasPath("/user/create")) {
                request.decodeCharacter(Charsets.UTF_8);
                String userId = request.getParameter("userId");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                User newUser = new User(userId, password, name, email);
                DataBase.addUser(newUser);
                logger.info("[Add User = {}]", newUser);
                response302Header(dos, "/index.html");
                return;
            }

            if (request.hasMethod(Method.POST) && request.hasPath("/user/login")) {
                request.decodeCharacter(Charsets.UTF_8);
                String userId = request.getParameter("userId");
                String password = request.getParameter("password");

                try {


                    User findUser = Optional.ofNullable(DataBase.findUserById(userId))
                            .filter(user -> user.hasPassword(password))
                            .orElseThrow(() -> new RuntimeException("로그인에 실패했습니다."));

                    logger.info("[login User] = {}", findUser);
                    try {
                        dos.writeBytes("HTTP/1.1 302 Found \r\n");
                        dos.writeBytes("Location: " + "/index.html" + "\r\n");
                        dos.writeBytes("Set-Cookie: " + "logined=true; Path=/" + "\r\n");
                        dos.writeBytes("\r\n");
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    } finally {
                        return;
                    }

                } catch (RuntimeException ex) {
                    logger.info("[login Fail]");
                    try {
                        dos.writeBytes("HTTP/1.1 302 Found \r\n");
                        dos.writeBytes("Location: " + "/user/login_failed.html" + "\r\n");
                        dos.writeBytes("Set-Cookie: " + "logined=false; Path=/" + "\r\n");
                        dos.writeBytes("\r\n");
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    } finally {
                        return;
                    }
                }
            }



            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (NullRequestException e) {
            logger.warn(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String findContentType(String resource) {
        int startIndexOfFileExtension = resource.lastIndexOf(".") + 1;
        String fileExtension = resource.substring(startIndexOfFileExtension);

        switch (fileExtension) {
            case "css": return "text/css";
            case "js": return "text/javascript";
            case "html": return "text/html";
            case "png": return "image/png";
            case "ico": return "image/vnd.microsoft.icon";
            case "eot": return "font/eot";
            case "svg": return "font/svg";
            case "ttf": return "font/ttf";
            case "woff": return "font/woff";
            case "woff2": return "font/woff2";
            default: throw new RuntimeException("지원하지 않는 리소스");
        }
    }

    private boolean isResource(Request request) {
        URL resource = FileIoUtils.class.getClassLoader().getResource("./static" + request.getPath());
        return Objects.nonNull(resource);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
