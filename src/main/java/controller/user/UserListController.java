package controller.user;


import controller.AbstractController;
import db.DataBase;
import model.User;
import utils.HandlebarsUtils;
import webserver.http.request.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class UserListController extends AbstractController {

    public static final String URL = "/user/list";
    public static final String LOGIN_PAGE = "/user/login.html";
    public static final String LOGIN_TRUE = "true";

    // TODO: isLoggedIn 위치를 (Controller 말고) 더 알맞은 곳으로 위치시키기
    private Boolean isLoggedIn(RequestHeader requestHeader) {
        String cookies = requestHeader.getCookie();
        Cookie cookie = Cookie.parseFrom(cookies);
        return cookie.getLogined().equals(LOGIN_TRUE);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();

        if(!isLoggedIn(requestHeader)){
            return HttpResponse.redirect(LOGIN_PAGE);
        }

        List<User> users = new ArrayList<>(DataBase.findAll());

        String userListTemplate = HandlebarsUtils.getUserListTemplate(URL, users);
        return HttpResponse.getDynamicView(userListTemplate);
    }
}
