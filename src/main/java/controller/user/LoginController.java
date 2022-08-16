package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static model.Constant.SET_COOKIE;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";
    private static final String USER_LOGIN_FAIL_PATH = "/user/login_failed.html";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        logger.debug("LoginController : {}", httpRequest.getRequestPath());

        User user = Optional.ofNullable(DataBase.findUserById(httpRequest.getParameter("userId")))
                .orElse(null);

        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("path", ROOT_PATH);
        if (user != null && user.matchPassword(httpRequest.getParameter("password"))) {
            cookieMap.put(SET_COOKIE, "logined=true; Path=" + ROOT_PATH);

            return HttpResponse.sendRedirect(ROOT_FILE, cookieMap);
        }
        cookieMap.put(SET_COOKIE, "logined=false; Path=" + ROOT_PATH);

        return HttpResponse.sendRedirect(USER_LOGIN_FAIL_PATH, cookieMap);
    }
}
