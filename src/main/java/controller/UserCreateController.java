package controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseFactory;

import java.util.Map;

public class UserCreateController extends GetController {

    public HttpResponse doGet(HttpRequest httpRequest) {
        Map<String, String> queryMap = httpRequest.getQueryMap();
        User user = User.from(queryMap);
        DataBase.addUser(user);
        return HttpResponseFactory.response302("/index.html");
    }
}
