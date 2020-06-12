package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.responses.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fallback controller
 */
public class NotFoundController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(NotFoundController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        log.debug("path: {}", request.getPath());
        response.setStatus(HttpStatus.NOT_FOUND);
        response.addHeader("Content-Type", "text/html;charset=utf-8");
        response.renderTemplate("/404.html");
    }
}
