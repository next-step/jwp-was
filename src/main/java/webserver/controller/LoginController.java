package webserver.controller;

import db.DataBase;
import model.User;
import webserver.exception.NotFoundUserException;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        RequestBody requestBody = request.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");
        User user = DataBase.findUserById(userId)
                .orElseThrow(NotFoundUserException::new);
        if (!user.isEqual(password)) {
            response.addCookie("logined=false");
            String location = "http://" + request.getRequestHeaders().get("Host") + "/user/login_failed.html";
            response.response302(location);
            return;
        }
        response.addCookie("logined=true");
        String location = "http://" + request.getRequestHeaders().get("Host") + "/index.html";
        response.response302(location);
    }
}
