package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.StaticResourceView;
import http.view.TemplateView;
import java.util.Optional;

public class UserLoginHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        return login(httpRequest);
    }

    private HttpResponse login(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        boolean isLoginSuccess = login(userId, password);
        if (!isLoginSuccess) {
            return new HttpResponse(new TemplateView("user/login_failed"));
        }

        HttpResponse httpResponse = new HttpResponse(new StaticResourceView("/index.html"));
        httpResponse.addCookie("logined", Boolean.TRUE.toString());
        return httpResponse;
    }

    private boolean login(String userId, String password) {
        return Optional.ofNullable(DataBase.findUserById(userId))
            .filter(user -> user.getPassword().equals(password))
            .isPresent();
    }
}
