package http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserCreateController extends UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("UserCreateController - doPost");

        final Map<String, String> requestBody = request.getRequestBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final String name = requestBody.get("name");
        final String email = requestBody.get("email");

        final int changeBefore = DataBase.findAll().size();
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        final int changeAfter = DataBase.findAll().size();

        if (changeAfter - 1 == changeBefore) {
            response.buildResponseLine(HttpStatus.FOUND);
            response.setContentType("text/html; charset=utf-8");
            response.setResponseBody("/index.html");
            response.print();
            return;
        }
        super.doPost(request, response);
    }

}
