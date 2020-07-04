package http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserLoginController extends UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("UserLoginController - doPost");
        final Map<String, String> requestBody = request.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User userById = DataBase.findUserById(userId);
        if (userById.getPassword().equals(password)) {
            response.buildResponseLine(HttpStatus.FOUND);
            response.setContentType("text/html; charset=utf-8");
            response.setCookie(true);
            response.setResponseBody("/index.html");
            response.print();
            return;
        }
        response.buildResponseLine(HttpStatus.FOUND);
        response.setContentType("text/html; charset=utf-8");
        response.setCookie(false);
        response.setResponseBody("/user/login_failed.html");
        response.print();

        super.doPost(request, response);
    }
}
