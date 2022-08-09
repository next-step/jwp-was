package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UserCreateController extends PostController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        createUser(request);
        try {
            response.redirect("/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUser(HttpRequest request) {
        RequestBody requestBody = request.getBody();
        Map<String, String> map = requestBody.getDataPairs();
        User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
        DataBase.addUser(user);
    }
}
