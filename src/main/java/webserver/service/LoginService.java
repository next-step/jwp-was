package webserver.service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.session.HttpSession;
import webserver.session.HttpSessionFactory;

import java.util.Objects;
import java.util.UUID;

import static webserver.controller.AbstractController.*;
import static webserver.session.HttpSession.SESSION_ID;

public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static final String COOKIE_LOGINED_SUCCESS_VALUE = "true";
    private static final String COOKIE_LOGINED_FAIL_VALUE = "false";

    private final HttpRequest request;
    private final HttpResponse response;

    public LoginService(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public void checkIdAndPassword(User requestUser) {
        logger.debug("webserver.request user : {}", requestUser);
        UserService userService = new UserService(request);
        User savedUser = userService.findByUserId(requestUser.getUserId());
        Cookie cookie = new Cookie();
        HttpSession session = request.getSession();
        String uuid = UUID.randomUUID().toString();

        if (Objects.isNull(savedUser) ||
                !savedUser.getPassword().equals(requestUser.getPassword())) {
            logger.debug("로그인 실패");
            session.setAttribute(LOGINED_KEY, COOKIE_LOGINED_FAIL_VALUE);
            HttpSessionFactory.makeSession(uuid, session);
            makeResponse(response, cookie, uuid, LOGIN_FAIL_URl);
            return ;
        }

        logger.debug("로그인 성공");
        session.setAttribute(LOGINED_KEY, COOKIE_LOGINED_SUCCESS_VALUE);
        HttpSessionFactory.makeSession(uuid, session);
        makeResponse(response, cookie, uuid, INDEX_URl);
    }

    private void makeResponse(HttpResponse response, Cookie cookie, String uuid, String path) {
        cookie.setCookie(SESSION_ID, uuid);
        response.setCookie(cookie);
        response.makeLocationPath(path);
        response.sendRedirect(request, response);
    }

}
