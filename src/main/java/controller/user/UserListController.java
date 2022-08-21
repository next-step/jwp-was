package controller.user;


import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarsUtils;
import webserver.http.request.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.LocalSessionStorage;
import webserver.http.session.SessionId;

import java.util.ArrayList;
import java.util.List;

import static webserver.http.session.SessionAttributes.SESSION_LOGIN_KEY;


public class UserListController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String URL = "/user/list.html";
    public static final String TEMPLATE_URL = "/user/list";
    public static final String LOGIN_PAGE = "/user/login.html";
    public static final String LOGIN_TRUE = "true";

    private Boolean isLoggedIn(RequestHeader requestHeader) {
        Cookie cookie = requestHeader.getCookie();
        try {
            SessionId sessionId = cookie.getSessionId();
            HttpSession session = LocalSessionStorage.getSession(sessionId);
            Object attribute = session.getAttribute(SESSION_LOGIN_KEY);
            return attribute.equals(LOGIN_TRUE);
        } catch (Exception e) {
            logger.debug("로그인하지 않은 사용자 입니다.");
            return false;
        }
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();

        if(!isLoggedIn(requestHeader)){
            return HttpResponse.redirect(LOGIN_PAGE);
        }

        List<User> users = new ArrayList<>(DataBase.findAll());

        String userListTemplate = HandlebarsUtils.getUserListTemplate(TEMPLATE_URL, users);
        return HttpResponse.getDynamicView(userListTemplate);
    }
}
