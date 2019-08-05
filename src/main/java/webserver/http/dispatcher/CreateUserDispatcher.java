package webserver.http.dispatcher;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class CreateUserDispatcher extends AbstractDispatcher<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserDispatcher.class);

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            String userId = request.getBodyRequest("userId");
            String password = request.getBodyRequest("password");
            String name = request.getBodyRequest("name");
            String email = request.getBodyRequest("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            response.redirect("/index.html", false);
        } catch (Exception e) {
            logger.error("[PROCESS][CREATE USER] failed. {}", e);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetRequest()) {
            doGet(request, response);
        }

        if (request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
