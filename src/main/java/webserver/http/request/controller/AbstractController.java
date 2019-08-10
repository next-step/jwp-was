package webserver.http.request.controller;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

public abstract class AbstractController implements Controller {

    protected static final String DEFAULT_ERROR_TEMPLATE = ViewType.TEMPLATE.getPrefix() + "/error";

    private boolean allowAll;

    protected AbstractController(boolean allowAll) {
        this.allowAll = allowAll;
    }

    @Override
    public boolean isAllowAll() {
        return allowAll;
    }

    @Override
    public ModelAndView process(HttpRequest httpRequest, HttpResponse httpResponse) {

        ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_TEMPLATE);
        HttpMethod method = httpRequest.getHttpMethod();

        // service logic
        if (method.isGet()) {
            modelAndView = getProcess(httpRequest, httpResponse);
        } else if (method.isPost()) {
            modelAndView = postProcess(httpRequest, httpResponse);
        } else if (method.isPut()) {
            modelAndView = putProcess(httpRequest, httpResponse);
        } else if (method.isDelete()) {
            modelAndView = deleteProcess(httpRequest, httpResponse);
        }
        return modelAndView;
    }

    ModelAndView postProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_TEMPLATE);
    }

    ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_TEMPLATE);
    }

    ModelAndView putProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_TEMPLATE);
    }

    ModelAndView deleteProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_TEMPLATE);
    }
}
