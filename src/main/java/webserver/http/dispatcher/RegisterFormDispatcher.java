package webserver.http.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class RegisterFormDispatcher<V extends ViewResolver> extends AbstractDispatcher<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(RegisterFormDispatcher.class);

    private V viewResolver;

    public RegisterFormDispatcher(V viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] view = viewResolver.render(request.getPath());
            response.writeHeader(request.getAccept(), view.length);
            response.writeBody(view);
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][HTML] failed. {}", e);
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
