package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;

public class UserController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return doPost(request, response);
    }

    @Override
    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        RequestBody requestBody = request.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");
        String name = requestBody.get("name");
        String email = requestBody.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        ModelAndView mav = new ModelAndView();
        mav.setView("redirect:/index.html");
        return mav;
    }
}
