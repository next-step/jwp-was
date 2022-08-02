package controller.user;


import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarsUtils;
import webserver.http.HttpMethod;
import webserver.http.request.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.response.HttpResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserListController {

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    public static final String URL = "/user/list";
    public static final String VIEW_PATH = "/user/list.html";
    public static final String LOGIN_PAGE = "/user/login.html";
    public static final String LOGIN_TRUE = "true";

    public HttpResponse run(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getRequestLine().getMethod();

        if (httpMethod.equals(HttpMethod.GET)) {
            return doGet(httpRequest);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Boolean isLoggedIn(RequestHeader requestHeader) {
        String cookies = requestHeader.getCookie();
        Cookie cookie = Cookie.parseFrom(cookies);
        return cookie.getLogined().equals(LOGIN_TRUE) ? true : false;
    }

    private HttpResponse doGet(HttpRequest httpRequest) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();

        if(!isLoggedIn(requestHeader)){
            return HttpResponse.redirect(LOGIN_PAGE);
        }

        List<User> users = DataBase.findAll()
                .stream().collect(Collectors.toList());

        String userListTemplate = HandlebarsUtils.getUserListTemplate(URL, users);
        return HttpResponse.getDynamicView(userListTemplate);
    }
}
