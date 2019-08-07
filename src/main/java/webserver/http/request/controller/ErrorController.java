package webserver.http.request.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

/**
 * @author : yusik
 * @date : 2019-08-07
 */
public class ErrorController extends AbstractController {

    @Override
    public ModelAndView process(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }
}
