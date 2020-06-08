package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.RedirectView;
import http.view.TemplateModel;
import http.view.TemplateView;
import java.util.Optional;
import model.User;

public class UserHandler {

    public HttpResponse create(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        HttpResponse httpResponse = new HttpResponse(new RedirectView("/index.html"));
        return httpResponse;
    }

    public HttpResponse list(HttpRequest httpRequest) {
        if (!isLogin(httpRequest)) {
            return new HttpResponse(new RedirectView("/user/login.html"));
        }

        TemplateModel templateModel = new TemplateModel();
        templateModel.add("users", DataBase.findAll());

        return new HttpResponse(new TemplateView("user/list", templateModel));
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getCookie("logined"))
            .filter(val -> val.equals(Boolean.TRUE.toString()))
            .isPresent();
    }

    public HttpResponse login(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");

        boolean isLoginSuccess = login(userId, password);
        if (!isLoginSuccess) {
            return new HttpResponse(new RedirectView("/user/login_failed.html"));
        }

        HttpResponse httpResponse = new HttpResponse(new RedirectView("/index.html"));
        httpResponse.addCookie("logined", Boolean.TRUE.toString());
        return httpResponse;
    }

    private boolean login(String userId, String password) {
        return Optional.ofNullable(DataBase.findUserById(userId))
            .filter(user -> user.getPassword().equals(password))
            .isPresent();
    }

}
