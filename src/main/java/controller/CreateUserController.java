package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    public void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
        logger.info("createUser :{}", user.toString());
        response.sendRedirect("/index.html");
    }

}
