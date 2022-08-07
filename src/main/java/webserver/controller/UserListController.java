package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import http.request.Protocol;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.io.IOException;
import java.util.Collections;

public class UserListController implements Controller {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String LOCATION = "Location";
    private static final String USER_LIST_TEMPLATE = "user/list";
    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public HttpResponse service(HttpRequest request) throws IOException {
        if (isLogin(request)) {
            return HttpResponse.of(
                    StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                    HttpHeader.from(Collections.singletonMap(CONTENT_TYPE, "text/html;charset=utf-8")),
                    handlebars.compile(USER_LIST_TEMPLATE).apply(Collections.singletonMap("users", DataBase.findAll()))
            );
        }
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                HttpHeader.from(Collections.singletonMap(LOCATION, "/user/login.html"))
        );
    }

    private boolean isLogin(HttpRequest request) {
        return request.getCookieValue("logined").equals("true");
    }
}