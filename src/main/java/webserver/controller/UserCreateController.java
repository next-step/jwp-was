package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

public class UserCreateController extends MethodController {
    @Override
    HttpResponse processGet(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    @Override
    public HttpResponse processPost(HttpRequest httpRequest) {
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
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isPathEqual(new Path("/user/create"));
    }
}
