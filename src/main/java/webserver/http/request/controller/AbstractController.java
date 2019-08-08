package webserver.http.request.controller;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

public abstract class AbstractController implements Controller {

    public static final String DEFAULT_ERROR_VIEW = "/error";

    @Override
    public ModelAndView process(HttpRequest httpRequest, HttpResponse httpResponse) {

        ModelAndView modelAndView = new ModelAndView("redirect::" + DEFAULT_ERROR_VIEW);
        HttpMethod method = httpRequest.getHttpMethod();

        // service logic
        if (method.isGet()) {
            modelAndView = getProcess(httpRequest, httpResponse);
        } else if (method.isPost()) {
            modelAndView = postProcess(httpRequest, httpResponse);
        }
        return modelAndView;
    }

    ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("redirect::" + DEFAULT_ERROR_VIEW);
    }

    ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("redirect::" + DEFAULT_ERROR_VIEW);
    }

    ModelAndView putProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("redirect::" + DEFAULT_ERROR_VIEW);
    }

    ModelAndView deleteProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("redirect::" + DEFAULT_ERROR_VIEW);
    }
}
