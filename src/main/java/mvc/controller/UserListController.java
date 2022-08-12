package mvc.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final String USER_LIST_TEMPLATE = "/user/list";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (isLogin(request)) {
            response.addBodyAttribute("users", DataBase.findAll());
            render(request, response, USER_LIST_TEMPLATE);
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    private boolean isLogin(HttpRequest request) {
        return request.getCookieValue("logined").equals("true");
    }
}
