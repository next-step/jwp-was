package webserver.service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Cookie;
import webserver.controller.AbstractController;
import webserver.request.HttpRequest;

import java.util.Objects;

public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static final String COOKIE_LOGINED_SUCCESS_VALUE = "true";
    private static final String COOKIE_LOGINED_FAIL_VALUE = "false";

    private final HttpRequest request;

    public LoginService(HttpRequest request) {
        this.request = request;
    }

    public Cookie checkIdAndPassword(User requestUser) {
        logger.debug("webserver.request user : {}", requestUser);
        UserService userService = new UserService(request);
        User savedUser = userService.findByUserId(requestUser.getUserId());
        Cookie cookie = new Cookie();
        if (Objects.isNull(savedUser) ||
                !savedUser.getPassword().equals(requestUser.getPassword())) {
            logger.debug("cookie 로그인 실패");
            return cookie.setCookie(AbstractController.LOGINED_KEY, COOKIE_LOGINED_FAIL_VALUE);
        }

        logger.debug("cookie 로그인 성공");
        return cookie.setCookie(AbstractController.LOGINED_KEY, COOKIE_LOGINED_SUCCESS_VALUE);
    }

    public String getRedirectUrl(Cookie cookie) {
        String isLogin = cookie.getCookie(AbstractController.LOGINED_KEY);
        if (!Boolean.parseBoolean(isLogin)) {
            return AbstractController.LOGIN_FAIL_URl;
        }

        return AbstractController.INDEX_URl;
    }
}
