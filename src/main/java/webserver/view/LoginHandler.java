package webserver.view;

import db.DataBase;
import model.User;
import webserver.AbstractHandler;
import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.ViewResolver;

import java.io.IOException;

import static webserver.view.SessionUtil.SESSION_KEY;

public class LoginHandler extends AbstractHandler {

    public LoginHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = DataBase.findUserById(request.getRequestBodyParameter("userId"));

        if (isLoginFail(user, request.getRequestBodyParameter("password"))) {
            response.response302Header("/user/login_failed.html");
        } else {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(SESSION_KEY, user);
            response.response302Header("/index.html");
        }
    }

    private boolean isLoginFail(User user, String password) {
        return user == null || !user.isPasswordMatch(password);
    }
}
