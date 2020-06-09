package controller;

import dto.Users;
import http.request.HttpRequest;
import http.request.QueryString;
import http.response.HttpResponse;

import java.io.IOException;

public class RequestController {

    private final UserController userController;

    public RequestController(UserController userController) {
        this.userController = userController;
    }

    public void createUser(HttpRequest request, HttpResponse httpResponse) {
        userController.createUser(new QueryString(request.getBody()), httpResponse);
    }

    public void login(HttpRequest request, HttpResponse httpResponse) {
        boolean isSuccessLogin = userController.login(new QueryString(request.getBody()), httpResponse);
    }

    public void userList(HttpRequest request, HttpResponse httpResponse) throws IOException {
        if (request.isLogin()) {
            Users users = userController.userList();
            httpResponse.returnHandlebar("user/list", users);
            return;
        }

        httpResponse.forward("/index.html");
    }

    public void request(HttpRequest request, HttpResponse httpResponse) {
        if (request.isStylesheet()) {
            httpResponse.viewStyleSheet(request.getPath());
            return;
        }

        httpResponse.forward(request.getPath());
    }
}
