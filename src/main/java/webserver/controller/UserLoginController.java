package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ResponseHandler;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParams;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;

import java.io.OutputStream;

public class UserLoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpRequestParams requestParams = request.getHttpRequestParams();

        User user = DataBase.findById(requestParams);
        String cookie = "Set-Cookie: ";

        if (user.isOwner(requestParams)) {
            log.info("login success : {} ", user.toString());
            cookie = cookie + "logined=true; Path=/";

            return HttpResponseResolver.redirect("/index.html", cookie);
        }

        cookie = cookie + "logined=false;";
        return HttpResponseResolver.redirect("/user/login_failed.html", cookie);
    }
}
