package controller;

import java.io.IOException;
import service.UserService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class ListUserController extends AbstractController {
    @Override
    Response doGet(Request request) throws IOException {
        boolean loggedIn = request.getCookie().contains("loggedIn=true");
        return UserService.getUserList(loggedIn);
    }

    @Override
    Response doPost(Request request) {
        return ResponseFactory.createNotImplemented();
    }
}
