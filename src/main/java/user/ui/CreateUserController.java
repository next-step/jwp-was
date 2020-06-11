package user.ui;

import controller.BaseController;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CreateUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        Map<String, String> user = httpRequest.getRequestParameters();
        if (path.equals("/user/create")) {
            String userId = user.getOrDefault("userId", "");
            String password = user.getOrDefault("password", "");
            String email = user.getOrDefault("email", "");
            String name = user.getOrDefault("name", "");
            DataBase.addUser(new User(userId, password, email, name));
        }
        httpResponse.sendRedirect("/index.html");
    }
}
