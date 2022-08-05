package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

public class UserCreateController implements Controller {
    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        DataBase.addUser(
                new User(
                        httpRequest.getParam("userId"),
                        httpRequest.getParam("password"),
                        httpRequest.getParam("name"),
                        httpRequest.getParam("email")
                )
        );
        return HttpResponse.redirect("/index.html");
    }

    @Override
    public boolean isMatchRequest(HttpRequest httpRequest) {
        return httpRequest.isMethodEqual(Method.POST) && httpRequest.isPathEqual(new Path("/user/create"));
    }
}
