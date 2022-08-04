package controller.user;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import controller.AbstractController;
import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Cookie;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String ROOT_FILE = "/index.html";
    public static final String ROOT_PATH = "/";
    private static final String USER_LOGIN_FAIL_PATH = "/user/login_failed.html";


    @Override
    public void doPost(Request request, Response response) {
        logger.debug("LoginController : {}", request.getRequestPath());

        Boolean isLogin = Optional.ofNullable(DataBase.findUserById(request.getParameter("userId")))
                .map(user -> StringUtils.equals(user.getPassword(), request.getParameter("password")))
                .orElse(false);

        if (isLogin) {
            response.setCookie(new Cookie("logined", "true", ROOT_PATH));
            response.sendRedirect(ROOT_FILE);
            return;
        }

        response.setCookie(new Cookie("logined", "false", ROOT_PATH));
        response.sendRedirect(USER_LOGIN_FAIL_PATH);
    }
}
