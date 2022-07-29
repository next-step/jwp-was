package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class UserCreateController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        final Map<String, String> payloads = httpRequest.getPayloads();
        final User user = new User(
                payloads.get("userId"),
                payloads.get("password"),
                payloads.get("name"),
                payloads.get("email")
        );

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }
}
