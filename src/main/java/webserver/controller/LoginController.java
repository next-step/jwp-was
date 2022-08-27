package webserver.controller;

import db.DataBase;
import model.User;
import webserver.cookie.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.session.HttpSession;
import webserver.session.HttpSessionRepository;

import java.util.UUID;

public class LoginController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        Cookie cookie = new Cookie();
        HttpSession session = initSession();

        if (isLogin(request)) {
            session.setAttribute("logined", "true");
            session.setAttribute("Path","/");

            cookie.addCookie("sessionId", session.getId());

            return HttpResponse.sendRedirect("/index.html", cookie);
        }

        session.setAttribute("logined", "false");

        cookie.addCookie("sessionId", session.getId());

        return HttpResponse.sendRedirect("/user/login_failed.html", cookie);
    }

    private HttpSession initSession() {
        HttpSession httpSession = HttpSessionRepository.addSession();
        return httpSession;
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String userId = httpRequest.getBody().getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            return false;
        }

        String pw = httpRequest.getBody().getParameter("password");
        return user.getPassword().equals(pw);
    }
}
