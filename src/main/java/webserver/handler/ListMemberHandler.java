package webserver.handler;

import db.DataBase;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ListMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String logined = httpRequest.getCookie("logined");

        if (logined.equals("true")) {
            ModelAndView modelAndView = new ModelAndView("/user/list");
            modelAndView.addAttribute("users", DataBase.findAll());
            return modelAndView;
        }

        return new ModelAndView("redirect:/user/login.html");
    }

}
