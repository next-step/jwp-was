package webserver.handler;

import db.DataBase;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.Request;
import webserver.http.Response;

public class ListMemberHandler implements Handler {

    @Override
    public ModelAndView handle(Request request, Response response) {
        String logined = request.getCookie("logined");

        if (logined.equals("true")) {
            ModelAndView modelAndView = new ModelAndView("/user/list");
            modelAndView.addAttribute("users", DataBase.findAll());
            return modelAndView;
        }

        return new ModelAndView("redirect:/user/login.html");
    }

}
