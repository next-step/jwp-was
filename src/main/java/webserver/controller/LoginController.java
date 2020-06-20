package webserver.controller;

import db.DataBase;
import model.User;
import webserver.exception.NotFoundUserException;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return new ModelAndView();
    }

    @Override
    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        ModelAndView mav = new ModelAndView();
        RequestBody requestBody = request.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");
        User user = DataBase.findUserById(userId)
                .orElseThrow(NotFoundUserException::new);

        if (!user.isEqual(password)) {
            response.addCookie("logined=false");
            mav.setView("redirect:/user/login_failed.html");
            return mav;
        }
        response.addCookie("logined=true");
        mav.setView("redirect:/index.html");
        return mav;
    }
}
