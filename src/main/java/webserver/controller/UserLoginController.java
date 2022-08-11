package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

public class UserLoginController extends MethodController {
    @Override
    HttpResponse processGet(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    @Override
    public HttpResponse processPost(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest.getParam("userId"));

        if (user == null || !user.isPasswordCorrect(httpRequest.getParam("password"))) {
            return HttpResponse.redirect("/user/login_failed.html",
                    Header.loginFailResponse()
            );
        }

        return HttpResponse.redirect("/index.html",
                Header.loginSuccessResponse()
        );
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isPathEqual(new Path("/user/login"));
    }
}
