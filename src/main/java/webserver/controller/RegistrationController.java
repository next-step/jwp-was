package webserver.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.request.RequestHeader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static webserver.http.ViewResolver.from;

public class RegistrationController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private UserService userService;

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("service process, registration user ");

        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");
        String name = httpParameter.getParameter("name");
        String email = httpParameter.getParameter("email");

        userService.add(new User(userId, password, name, email));

        from(httpRequest, httpResponse).redirect("/index.html");
    }

    @Override
    protected void setUserService(UserService userService) {
        this.userService = userService;
    }
}
