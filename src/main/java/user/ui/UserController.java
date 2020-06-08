package user.ui;

import db.DataBase;
import http.RequestLine;
import http.ResponseObject;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final RequestLine requestLine;

    public UserController(final RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public ResponseObject mapping() {
        String method = requestLine.getMethodName();

        if (method.equals("GET")) {
            return mappingByGetMethod();
        }
        return mappingByPostMethod();
    }

    private ResponseObject mappingByGetMethod() {
        String path = requestLine.getPath();
        return new ResponseObject(200, "/index.html", String.format("./templates%s", path));
    }

    private ResponseObject mappingByPostMethod() {
        String path = requestLine.getPath();
        if (path.equals("/user/create")) {
            Map<String, String> requestParams = requestLine.getParams();
            String userId = requestParams.getOrDefault("userId", "");
            String password = requestParams.getOrDefault("password", "");
            String email = requestParams.getOrDefault("email", "");
            String name = requestParams.getOrDefault("name", "");
            User user = new User(userId, password, email, name);
            DataBase.addUser(user);
            logger.debug(user.toString());
            return new ResponseObject(302, "/index.html", path);
        }

        if (path.equals("/user/login")) {
            try {
                Map<String, String> requestParams = requestLine.getParams();
                String userId = requestParams.getOrDefault("userId", "");
                String password = requestParams.getOrDefault("password", "");
                User user = DataBase.findUserById(userId);
                if (user.isCorrectPassword(password)) {
                    return new ResponseObject(200, "/index.html", path);
                }
                return new ResponseObject(302, "/user/login_failed.html", path);

            } catch (NullPointerException e) {
                return new ResponseObject(302, "/user/login_failed.html", path);
            }
        }

        return null;
    }
}
