package http;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoginController extends DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("UserLoginController - doPost");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User userById = DataBase.findUserById(userId);
        if (userById.getPassword().equals(password)) {

            response.sendRedirect("/index.html");
            response.setCookie(true);
            return;
        }

        response.sendRedirect("/user/login_failed.html");

        super.doPost(request, response);
    }
}
