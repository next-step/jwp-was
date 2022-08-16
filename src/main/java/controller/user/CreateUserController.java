package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    public static final String ROOT_FILE = "/index.html";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        logger.debug("CreateUserController : {}", httpRequest.getRequestPath());

        DataBase.addUser(new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        ));
        return HttpResponse.sendRedirect(ROOT_FILE);
    }
}
