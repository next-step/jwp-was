package mvc.controller;

import com.github.jknack.handlebars.Handlebars;
import db.DataBase;
import http.request.protocol.Protocol;
import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;

import java.io.IOException;
import java.util.Collections;

public class UserListController extends AbstractController {
    private static final String USER_LIST_TEMPLATE = "user/list";
    private final Handlebars handlebars;

    public UserListController(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if (isLogin(request)) {
            response.buildResponse(
                    StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                    HttpHeader.from(Collections.singletonMap(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8")),
                    handlebars.compile(USER_LIST_TEMPLATE).apply(Collections.singletonMap("users", DataBase.findAll()))
            );
        }
        response.buildResponse(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                HttpHeader.from(Collections.singletonMap(HttpHeader.LOCATION, "/user/login.html"))
        );
    }

    private boolean isLogin(HttpRequest request) {
        return request.getCookieValue("logined").equals("true");
    }
}
