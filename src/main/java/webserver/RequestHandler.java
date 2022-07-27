package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpMethod;
import webserver.http.request.Request;
import webserver.http.request.RequestHeader;
import webserver.http.response.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static model.Constant.EXTENSION_SPERATOR;
import static model.Constant.ROOT_FILE;
import static utils.HandlebarsUtils.loader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String USER_LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final String USER_LIST_PATH = "/user/list.html";
    private static final String COOKIE = "Cookie";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = new Request(in);
            String path = getPathFromRequest(request);
            Response response = new Response(out, path);

            if (StringUtils.equals(request.getRequestPath(), USER_CREATE_PATH) && request.getHttpMethod() == HttpMethod.POST) {
                createUser(response, request.getRequestBody());
                return;
            }

            if (StringUtils.equals(request.getRequestPath(), USER_LOGIN_PATH) && request.getHttpMethod() == HttpMethod.POST) {
                loginUser(response, request.getRequestBody());
                return;
            }

            if (StringUtils.equals(request.getRequestPath(), USER_LIST_PATH) && request.getHttpMethod() == HttpMethod.GET) {
                getUsers(response, request.getHeader());
                return;
            }

            byte[] body = response.getBody(path);

            response200Header(response, body.length);
            responseBody(response, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private void getUsers(Response response, RequestHeader requestHeader) throws IOException {
        if (isLoginStatus(requestHeader)) {
            response302Header(response, USER_LIST_PATH);
            return;
        }
        Collection<User> users = DataBase.findAll();
        String loadData = loader(users, USER_LIST_PATH.substring(0, USER_LIST_PATH.indexOf(EXTENSION_SPERATOR)));
        byte[] body = loadData.getBytes(StandardCharsets.UTF_8);

        response200Header(response, body.length);
        responseBody(response, body);
    }

    private boolean isLoginStatus(RequestHeader requestHeader) {
        return Stream.of(requestHeader.getHeaders().get(COOKIE))
                .anyMatch(loginStatus -> StringUtils.equals(loginStatus, "logined=true"));
    }

    private void loginUser(Response response, Map<String, String> requestBody) {
        User loginUser = Optional.ofNullable(DataBase.findUserById(requestBody.get("userId")))
                .filter(user -> StringUtils.equals(user.getPassword(), requestBody.get("password")))
                .orElse(null);

        if (loginUser != null) {
            response.getHeaders().add(Map.of("Set-Cookie", "logined=true; Path=/"));
            response302Header(response, ROOT_FILE);
            return;
        }
        response.getHeaders().add(Map.of("Set-Cookie", "logined=false; Path=/"));
        response302Header(response, USER_LOGIN_FAIL_PATH);

    }

    private void createUser(Response response, Map<String, String> requestBody) {
        User user = new User(requestBody.get("userId"), requestBody.get("password"), requestBody.get("name"), requestBody.get("email"));
        logger.debug("User : {}", user);
        DataBase.addUser(user);

        response302Header(response, ROOT_FILE);
    }

    private String getPathFromRequest(Request request) {
        return request.getRequestPath();
    }

    private void response200Header(Response response, int lengthOfBodyContent) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(StringUtils.join(response.getHeaders(), ": "));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(Response response, String redirectPath) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(Response response, byte[] body) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
