package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;
import utils.ConvertUtils;

public class CreateUserController extends AbstractController {

    @Override
    public String getPath() {
        return "/user/create";
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = ConvertUtils.convertMapToObject(request.getParameters(), User.class);
        UserService.save(user);
//        byte[] body = user.toString().getBytes();

        response.sendRedirect("http://localhost:8080/index.html");
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
