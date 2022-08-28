package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionManagement;

import static model.Constant.JSESSIONID;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";
    private static final String USER_LOGIN_FAIL_PATH = "/user/login_failed.html";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        logger.debug("LoginController : {}", httpRequest);

        HttpSession httpSession = SessionManagement.save();
        Cookie cookie = new Cookie();

        if (isLogin(httpRequest)) {
            httpSession.setAttribute("logined", "true");
            httpSession.setAttribute("Path", "/");

            cookie.setCookie(JSESSIONID, httpSession.getId());
            return HttpResponse.sendRedirect(ROOT_FILE, cookie);
        }

        httpSession.setAttribute("logined", "false");
        cookie.setCookie(JSESSIONID, httpSession.getId());
        return HttpResponse.sendRedirect(USER_LOGIN_FAIL_PATH, cookie);
    }

    private boolean isLogin(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest.getParameter("userId"));

        if (user == null) {
            return false;
        }

        return user.matchPassword(httpRequest.getParameter("password"));
    }
}
