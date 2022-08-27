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
        if (isLogin(request)) {
            String sessionId = initSession();

            cookie.addCookie("logined", "true;");
            cookie.addCookie("Path","/");

            return HttpResponse.sendRedirect("/index.html", cookie);
        }
        cookie.addCookie("logined", "false");
        return HttpResponse.sendRedirect("/user/login_failed.html", cookie);
    }

    private String initSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = HttpSessionRepository.addSession(String.valueOf(uuid));
        httpSession.setAttribute("logined", "true");

        return httpSession.getId();
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
