package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;

public class SignUpController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        DataBase.addUser(new User(httpRequest.body("userId"), httpRequest.body("password"),
                httpRequest.body("name"), httpRequest.body("email")));
        httpResponse.sendRedirect("/index.html");
    }
}
