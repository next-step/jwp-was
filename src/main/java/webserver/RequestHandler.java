package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            String path = httpRequest.getPath();

            DataOutputStream dos = new DataOutputStream(out);
            String fileExtension = FileIoUtils.getFileExtension(path);
            String contentType = ContentType.selectContent(fileExtension);

            byte[] body = "Hello World22".getBytes();
            if (path.equals("/user/create")) {
                Map<String, String> parameters = httpRequest.getBody().getParameter();
                createUser(parameters, dos, httpResponse);

                return;
            } else if (path.equals("/user/login")) {
                Map<String, String> parameters = httpRequest.getBody().getParameter();
                loginUser(parameters, dos, httpResponse);

                return;
            } else if (path.equals("/user/list")) {
                listUser(httpRequest.getCookie(), dos, contentType, httpResponse);

                return;
            }
            httpResponse.forward(path);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(Map<String, String> queryString, DataOutputStream dos,HttpResponse httpResponse) {
        User user = new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );

        DataBase.addUser(user);
        logger.debug("createdUserId : {}", user.getUserId());

        System.out.println("012321312312312312");

        httpResponse.sendRedirect("/index.html");

    }

    public void loginUser(Map<String, String> parameters, DataOutputStream dos, HttpResponse httpResponse) {
        User loginUser = DataBase.findUserById(parameters.get("userId"));
        boolean isLogin = !Objects.isNull(loginUser) && parameters.get("password").equals(loginUser.getPassword());

        httpResponse.getHeaders().add(Cookie.SET_COOKIE, Cookie.setLoginCookie(String.valueOf(isLogin)));
        httpResponse.sendRedirect(isLogin ? "/index.html" : "/login_failed.html");

    }

    public void listUser(Cookie cookie, DataOutputStream dos, String contentType, HttpResponse httpResponse) throws IOException {

        if (!Boolean.parseBoolean(cookie.getValue("logined"))) {
            httpResponse.sendRedirect("/login.html");
            return;
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        byte[] loaded = HandleBarsTemplate.load("user/list", users).getBytes();

        httpResponse.forwardBody(loaded);
        return;
    }

}
