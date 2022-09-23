package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;
import webserver.http.domain.RequestBody;

public class SignUpController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.requestBody();

        DataBase.addUser(new User(requestBody.userId(), requestBody.password(),
                requestBody.name(), requestBody.email()));
        httpResponse.sendRedirect("/index.html");
    }
}
