package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractHandler implements Handler {

    protected ViewResolver viewResolver;

    public AbstractHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.isPostRequest()) {
            doPost(request, response);
        }

        if (request.isGetRequest()) {
            doGet(request, response);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
    }
}
