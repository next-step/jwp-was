package http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateController extends UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("UserCreateController - doGet");
        doPost(request, response);
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("UserCreateController - doPost");

        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");

        final int changeBefore = DataBase.findAll().size();
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        final int changeAfter = DataBase.findAll().size();

        if (changeAfter - 1 == changeBefore) {
            response.sendRedirect("/index.html");
            return;
        }
        super.doPost(request, response);
    }

}
