package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Map;

public class UserController implements Controller {

    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        if (req.isGet()) {
            doGet(req, resp);
        }
    }

    private void doGet(HttpRequest request, HttpResponse response) {
        Map<String, String> queryParameters = request.getRequestLine().getQueryParameters();
        String userId = queryParameters.get("userId");
        String password = queryParameters.get("password");
        String name = queryParameters.get("name");
        String email = queryParameters.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }


}
