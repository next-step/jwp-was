package webserver.controller;

import webserver.ModelAndView;
import webserver.exception.MethodNotAllowedException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class AbstractController implements Controller {

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

    protected ModelAndView doGet(HttpRequest request, HttpResponse response) {
        return new ModelAndView();
    }

    protected ModelAndView doPost(HttpRequest request, HttpResponse response) {
        return new ModelAndView();
    }
}
