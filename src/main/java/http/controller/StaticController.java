package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.view.StaticViewResolver;
import http.view.View;
import http.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * controller for serving static resources like js, css, fonts
 */
public class StaticController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(StaticController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            final ViewResolver resolver = new StaticViewResolver();
            final View view = resolver.resolve(request.getPath());
            view.render(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.response500Error();
        }
    }
}
