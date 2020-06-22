package webserver.controller;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.ModelAndView;
import webserver.http.HttpSession;
import webserver.http.HttpSessionContainer;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Collection;

public class UserListController extends AbstractController {

    @Override
    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        ModelAndView mav = new ModelAndView();
        String jSessionId = request.getSessionId();
        if (StringUtils.isNull(jSessionId)) {
            mav.setView("redirect:/user/login.html");
            return mav;
        }
        HttpSession httpSession = HttpSessionContainer.get(jSessionId);
        String logined = (String) httpSession.getAttribute("logined");
        if ("true".equals(logined)) {
            Collection<User> users = DataBase.findAll();
            mav.addModel("users", users);
            mav.setView("/user/list.html");
            return mav;
        }
        mav.setView("redirect:/user/login.html");
        return mav;
    }
}
