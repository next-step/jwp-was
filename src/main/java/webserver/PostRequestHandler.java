package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public class PostRequestHandler implements MethodRequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(PostRequestHandler.class);

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException {
        RequestUri uri = request.getRequestLine().getUri();
        RequestBody requestBody = request.getRequestBody();

        if ("/user/create".equals(uri.getPath())) {
            User user = new User(requestBody.getValue("userId"), requestBody.getValue("password"),
                    requestBody.getValue("name"), requestBody.getValue("email"));
            DataBase.addUser(user);
            logger.debug("User : {}", user);

            response.response302Header("/index.html", false);
        }

        if ("/user/login".equals(uri.getPath())) {
            User user = DataBase.findUserById(requestBody.getValue("userId"));

            if (isLoginSuccess(user, requestBody.getValue("password"))) {
                response.response302Header("/user/login_failed.html", false);
            } else {
                response.response302Header( "/index.html", true);
            }
        }
    }

    private boolean isLoginSuccess(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
