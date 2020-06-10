package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
public class UserCreateController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = User.builder()
                .userId(httpRequest.getBody("userId"))
                .password(httpRequest.getBody("password"))
                .name(httpRequest.getBody("name"))
                .email(httpRequest.getBody("email"))
                .build();

        log.debug("User Create : {}", user);
        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }
}
