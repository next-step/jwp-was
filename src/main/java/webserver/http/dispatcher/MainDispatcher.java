package webserver.http.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainDispatcher<V extends ViewResolver> extends AbstractDispatcher<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(MainDispatcher.class);

    private V viewResolver;

    public MainDispatcher(V viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            View view = viewResolver.render(request.getPath());
            response.writeHeader(request.getAccept(), view.getLength());
            response.writeBody(view.getBody());
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][MAIN] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {

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
