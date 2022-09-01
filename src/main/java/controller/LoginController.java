package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.*;

import java.util.Objects;

import static webserver.Cookie.*;

public class LoginController extends AbstractController {

    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User loginUser = DataBase.findUserById(request.getBodyParameter("userId"));
        boolean isLogin = !Objects.isNull(loginUser) && request.getBodyParameter("password").equals(loginUser.getPassword());
        Cookie cookie = new Cookie();

        response.getHeaders().setCookie(cookie);

        // session 변경
        HttpSession session = request.getSession();
        HttpSessionStorage httpSessionStorage = request.getSessionStorage();

        session.setAttribute("logined", String.valueOf(isLogin));
        httpSessionStorage.addSession(session);

        response.getHeaders().getCookie().setCookie(session.setSessionId(), response.getHeaders());

        if (!isLogin) {
            response.sendRedirect("/login_failed.html");
            return;
        }

        response.sendRedirect("/index.html");
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }
}
