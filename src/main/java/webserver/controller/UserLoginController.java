package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserLoginController implements Controller {
    @Override
    public HttpResponse process(HttpRequest httpRequest) throws IOException, URISyntaxException {
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
    public boolean isMatchRequest(HttpRequest httpRequest) {
        return httpRequest.isMethodEqual(Method.POST) && httpRequest.isPathEqual(new Path("/user/login"));
    }
}
