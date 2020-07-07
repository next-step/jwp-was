package http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateController extends DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

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

        logger.debug("create user check - after: {}, before: {}", changeAfter, changeBefore);
        if (changeAfter - 1 == changeBefore) {
            logger.debug("user create complete");
            response.sendRedirect("/index.html");
            return;
        }
        super.doPost(request, response);
    }

}
