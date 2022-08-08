package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    public static final String ROOT_FILE = "/index.html";

    @Override
    public void doPost(Request request, Response response) {
        logger.debug("CreateUserController : {}", request.getRequestPath());

        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
        response.sendRedirect(ROOT_FILE);
    }
}
