package webserver.controller;

import webserver.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public ModelAndView service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            return doGet(request, response);
        }
        if (request.isPost()) {
            return doPost(request, response);
        }
        throw new MethodNotAllowedException();
    }

    protected abstract ModelAndView doGet(HttpRequest request, HttpResponse response);

    protected abstract ModelAndView doPost(HttpRequest request, HttpResponse response);
}
