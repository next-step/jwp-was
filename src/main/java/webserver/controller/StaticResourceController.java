package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import static webserver.http.ViewResolver.from;

public class StaticResourceController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceController.class);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("## access static resource {}", httpRequest.getPath());
        from(httpRequest, httpResponse).forward(httpRequest.getPath());
    }
}
