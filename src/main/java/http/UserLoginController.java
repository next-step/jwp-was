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
        parameterValidate(request, response);
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User userById = DataBase.findUserById(userId);
        if (userById == null) {
            response.setCookie(false);
            response.sendRedirect("/user/login_failed.html");
        }

        if (userById.getPassword().equals(password)) {
            response.setCookie(true);
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private void parameterValidate(final HttpRequest request, final HttpResponse response) {
        if (request.getParameter("userId") == null ||
                request.getParameter("password") == null ) {
            response.setCookie(false);
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
