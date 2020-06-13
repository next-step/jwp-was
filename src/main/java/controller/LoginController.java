package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;
import utils.ConvertUtils;

public class LoginController extends AbstractController {

    @Override
    public String getPath() {
        return "/user/login";
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = ConvertUtils.convertMapToObject(request.getParameters(), User.class);

        boolean isSuccess = UserService.login(user.getUserId(), user.getPassword());

        if (isSuccess) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("http://localhost:8080/index.html");
        } else {
            response.sendRedirect("http://localhost:8080/user/login_failed.html");
        }
    }
}
