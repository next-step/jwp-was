package webserver.http.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public class CreateUserController extends AbstractController<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            User user = extractUser(request);
            DataBase.addUser(user);
            response.addCookie(new Cookie("logined", "false"));
            response.redirect("/index");
        } catch (Exception e) {
            logger.error("[PROCESS][CREATE USER] failed. {}", e);
        }
    }

    private User extractUser(HttpRequest request) {
        String userId = request.getBodyParameter("userId");
        String password = request.getBodyParameter("password");
        String name = request.getBodyParameter("name");
        String email = request.getBodyParameter("email");

        return new User(userId, password, name, email);
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        if (request.isGetRequest()) {
            try {
                doGet(request, response);
            } catch (IOException e) {
                logger.error("[PROCESS][CREATE USER] failed. {}", e);
            }
        }

        if (request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
