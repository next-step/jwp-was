package webserver.http.request.controller;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

public abstract class AbstractController implements Controller {

    public static final String DEFAULT_ERROR_VIEW = "/error";

    @Override
    public ModelAndView process(HttpRequest httpRequest, HttpResponse httpResponse) {

        ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
        HttpMethod method = httpRequest.getHttpMethod();
        // service logic
        if (HttpMethod.GET == method) {
            modelAndView = getProcess(httpRequest, httpResponse);
        } else if (HttpMethod.POST == method) {
            modelAndView = postProcess(httpRequest, httpResponse);
        }
        return modelAndView;
    }

    ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }

    ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }
}
