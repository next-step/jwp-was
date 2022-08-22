package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.LocalSessionStorage;

import java.util.Map;

import static webserver.http.session.SessionAttributes.SESSION_KEY_LOGIN;
import static webserver.http.session.SessionAttributes.SESSION_VALUE_LOGIN;

public class LogInController extends AbstractController {

    public static final String URL = "/user/login";
    public static final String successRedirectPath = "/index.html";
    public static final String failRedirectPath = "/user/login_failed.html";

    private void setLoginSession(HttpResponse httpResponse){
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(SESSION_KEY_LOGIN, SESSION_VALUE_LOGIN);

        LocalSessionStorage.addSession(httpSession);
        httpResponse.getResponseHeader().setCookie(HttpSession.KEY + "=" + httpSession.getId() + "; Path=/");
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getRequestBody().getContents();
        String userId = body.get("userId");
        String password = body.get("password");

        User user = DataBase.findUserById(userId);

        if (user.getPassword().equals(password)) {
            HttpResponse successResponse = HttpResponse.redirect(successRedirectPath);
            setLoginSession(successResponse);
            return successResponse;
        }

        HttpResponse failResponse = HttpResponse.redirect(failRedirectPath);
        return failResponse;
    }
}
