package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Users;
import service.UserService;
import utils.TemplateUtils;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class ListUserController extends AbstractController {
    @Override
    Response doGet(Request request) throws IOException {
        boolean loggedIn = request.getCookie().contains("loggedIn=true");
        if (!loggedIn) {
            return ResponseFactory.createRedirect("/user/login.html");
        }
        String body = renderUserList(UserService.getUsers());
        return ResponseFactory.createOK(body);
    }

    @Override
    Response doPost(Request request) {
        return ResponseFactory.createNotImplemented();
    }

    private static String renderUserList(Users users) throws IOException {
        String templateLocation = "/user/list.html";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("users", users.toList());
        return TemplateUtils.render(templateLocation, parameterMap);
    }
}
