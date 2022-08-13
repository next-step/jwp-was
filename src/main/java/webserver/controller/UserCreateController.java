package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

import java.util.Map;

public class UserCreateController extends PostController {

    @Override
    public HttpResponse doPost(HttpRequest request) {
        createUser(request);
        return HttpResponse.sendRedirect("/index.html", "");
    }

    private void createUser(HttpRequest request) {
        RequestBody requestBody = request.getBody();
        Map<String, String> map = requestBody.getDataPairs();
        User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
        DataBase.addUser(user);
    }
}
