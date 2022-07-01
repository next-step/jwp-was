package users.controller;

import db.DataBase;
import mvc.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import users.model.User;
import was.http.HttpRequest;
import was.http.HttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        log.debug("user : {}", user);
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
