package controller.user;

import controller.AbstractController;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpResponseCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        logger.debug("User : {}", user);
        DataBase.addUser(user);
        response.addHeader("Location", "/index.html");
        response.sendRedirect(HttpResponseCode.REDIRECT_300);
    }
}
