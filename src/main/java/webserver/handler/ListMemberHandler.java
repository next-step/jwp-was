package webserver.handler;

import db.DataBase;
import webserver.Handler;
import webserver.HttpSessionHandler;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class ListMemberHandler implements Handler {

    private final HttpSessionHandler httpSessionHandler;

    public ListMemberHandler(HttpSessionHandler httpSessionHandler) {
        this.httpSessionHandler = httpSessionHandler;
    }

    @Override
    public ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpSessionHandler.getHttpSession(httpRequest, httpResponse);
        Object logined = httpSession.getAttribute("logined");

        if (logined != null && (boolean) logined) {
            ModelAndView modelAndView = new ModelAndView("/user/list");
            modelAndView.addAttribute("users", DataBase.findAll());
            return modelAndView;
        }

        return new ModelAndView("redirect:/user/login.html");
    }

}
