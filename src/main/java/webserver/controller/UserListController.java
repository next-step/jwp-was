package webserver.controller;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestHeaders;
import webserver.response.HttpResponse;

import java.util.Collection;

public class UserListController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        ModelAndView mav = new ModelAndView();
        RequestHeaders requestHeaders = request.getRequestHeaders();
        String cookie = requestHeaders.get("Cookie");
        if (StringUtils.isNotBlank(cookie) && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            mav.addModel("users", users);
            mav.setView("/user/list.html");
            return mav;
        }
        mav.setView("redirect:/user/login.html");
        return mav;
    }

    @Override
    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        return new ModelAndView();
    }
}
