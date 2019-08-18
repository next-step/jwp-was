package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ResponseHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParams;
import webserver.http.response.HttpResponse;

import java.io.OutputStream;

public class UserCreateController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public void handle(OutputStream out, HttpRequest request) {
        HttpRequestParams requestParams = request.getHttpRequestParams();

        User user = User.of(requestParams);
        DataBase.addUser(user);
        log.info("register : {}", user.toString());

        HttpResponse response = new HttpResponse().found("/index.html", null);

        ResponseHandler.response(out, response);
    }
}
