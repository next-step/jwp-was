package webserver.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainController<V extends ViewResolver> extends AbstractController<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private V viewResolver;

    public MainController(V viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            View view = viewResolver.render(request.getPath());
            response.writeHeader(HttpStatus.OK, request.getAccept(), view.getLength());
            response.writeBody(view.getBody());
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][MAIN] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            response.error(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (IOException e) {
            logger.error("[PROCESS][MAIN] failed. {}", e);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGetRequest()) {
            doGet(request, response);
        }

        if (request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
