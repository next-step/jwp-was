package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.controller.Controller;
import webserver.controller.IndexController;
import webserver.controller.UserFormController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginFormController;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> requestMapping = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initRequestMapping();
    }

    private void initRequestMapping() {
        requestMapping.put("/", new IndexController());
        requestMapping.put("/index.html", new IndexController());
        requestMapping.put("/user/form.html", new UserFormController());
        requestMapping.put("/user/login.html", new UserLoginFormController());
        requestMapping.put("/user/list", new UserListController());
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            handle(request, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();
        HttpMethod method = requestLine.getMethod();
        String requestBody = request.getRequestBody();
        HttpHeaders headers = request.getHeaders();

        Controller controller = requestMapping.get(path);
        if (controller != null) {
            controller.handle(request, response);
            return;
        }

        if (method.isPost() && "/user/create".equals(path)) {
            createUser(requestBody);
            response.sendRedirect("/index.html");
            return;
        }

        if (method.isPost() && "/user/login".equals(path)) {
            if (isLoggedIn(requestBody)) {
                response.addHeader("Set-Cookie", "logined=true; Path=/");
                response.sendRedirect("/index.html");
                return;
            }
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
            return;
        }

        response.addHeader("Content-Type", headers.getAccept());
        response.forward(path);
    }

    private void createUser(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    private boolean isLoggedIn(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        User user = DataBase.findUserById(userId);
        return user != null && password.equals(user.getPassword());
    }
}
