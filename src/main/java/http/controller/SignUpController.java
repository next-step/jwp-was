package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class SignUpController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

    private final UserService userService = new UserService();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final String userId = request.getAttributeFromForm("userId");
        final String password = request.getAttributeFromForm("password");
        final String name = request.getAttributeFromForm("name");
        final String email = request.getAttributeFromForm("email");
        final User newUser = new User(userId, password, name, email);
        log.debug("new user: {}", newUser);
        userService.signUp(newUser);
        response.sendRedirect("/index.html");
    }
}
