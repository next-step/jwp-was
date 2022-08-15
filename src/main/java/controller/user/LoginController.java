package controller.user;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import controller.AbstractController;
import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String ROOT_PATH = "/";
    public static final String ROOT_FILE = "/index.html";
    private static final String USER_LOGIN_FAIL_PATH = "/user/login_failed.html";


    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        logger.debug("LoginController : {}", httpRequest.getRequestPath());

        Boolean isLogin = Optional.ofNullable(DataBase.findUserById(httpRequest.getParameter("userId")))
                .map(user -> StringUtils.equals(user.getPassword(), httpRequest.getParameter("password")))
                .orElse(false);

        Map<String, String> cookieMap = new HashMap<String, String>();
        cookieMap.put("path", ROOT_PATH);
        if (isLogin) {
            cookieMap.put("logined", "true");

            HttpResponse httpResponse = HttpResponse.sendRedirect(ROOT_FILE, cookieMap);
//            httpResponse.setCookie(new Cookie("logined", "true", ROOT_PATH));
//            httpResponse.setCookie(new Cookie(cookieMap));

            return httpResponse;
        }
        cookieMap.put("logined", "false");
        HttpResponse httpResponse = HttpResponse.sendRedirect(USER_LOGIN_FAIL_PATH, cookieMap);
//        httpResponse.setCookie(new Cookie(cookieMap));

        return httpResponse;
    }
}
