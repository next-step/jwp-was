package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParams;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionManager;

public class UserLoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpRequestParams requestParams = request.getHttpRequestParams();

        User user = DataBase.findById(requestParams);

        if (user.isOwner(requestParams)) {
            log.info("login success : {} ", user.toString());

            HttpSession httpSession = HttpSessionManager.getSession(null);
            httpSession.setAttribute("user", user);

            return HttpResponseResolver.redirect("/index.html", httpSession.setCookie());
        }

        return HttpResponseResolver.redirect("/user/login_failed.html");
    }
}
