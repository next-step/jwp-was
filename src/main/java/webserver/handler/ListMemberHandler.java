package webserver.handler;

import db.DataBase;
import webserver.Handler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class ListMemberHandler implements Handler {

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession();
        Object logined = httpSession.getAttribute("logined");

        if (logined != null && (boolean) logined) {
            ModelAndView modelAndView = new ModelAndView("/user/list");
            modelAndView.addAttribute("users", DataBase.findAll());
            return modelAndView;
        }

        return new ModelAndView("redirect:/user/login.html");
    }

}
