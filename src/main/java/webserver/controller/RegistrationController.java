package webserver.controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ViewResolver;
import webserver.http.request.RequestHeader;

public class RegistrationController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("service process, registration user ");

        HttpParameter httpParameter = httpRequest.getMergedHttpParameter();
        String userId = httpParameter.getParameter("userId");
        String password = httpParameter.getParameter("password");
        String name = httpParameter.getParameter("name");
        String email = httpParameter.getParameter("email");

        userService.add(new User(userId, password, name, email));

        ViewResolver.from(httpRequest, httpResponse).redirect("/index.html");
    }

}
