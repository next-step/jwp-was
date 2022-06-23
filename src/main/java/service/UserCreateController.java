package service;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Map;

public class UserCreateController extends GetController {

    public HttpResponse doGet(HttpRequest httpRequest) {
        Map<String, String> queryMap = httpRequest.getQueryMap();
        User user = User.from(queryMap);
        DataBase.addUser(user);
        return HttpResponse.response302("/index.html");
    }
}
