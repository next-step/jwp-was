package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpSession;
import webserver.http.header.Header;
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

        if (isLoginFail(httpRequest, user)) {
            return HttpResponse.redirect("/user/login_failed.html",
                    Header.loginFailResponse(httpRequest.getSessionId())
            );
        }

        HttpSession session = httpRequest.getSession();
        String sessionId = session.getId();
        return HttpResponse.redirect("/index.html",
                Header.loginSuccessResponse(sessionId)
        );
    }

    private static boolean isLoginFail(HttpRequest httpRequest, User user) {
        return user == null || !user.isPasswordCorrect(httpRequest.getParam("password"));
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isPathEqual(new Path("/user/login"));
    }
}
