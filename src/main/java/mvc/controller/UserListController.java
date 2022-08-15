package mvc.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.session.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserListController extends AbstractController {
    private static final String USER_LIST_TEMPLATE = "/user/list";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (isLogin(request.getSession())) {
            request.addBodyAttribute("users", DataBase.findAll());
            render(request, response, USER_LIST_TEMPLATE);
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    private boolean isLogin(HttpSession session) {
        Object user = session.getAttribute("user");
        return user != null;
    }
}
