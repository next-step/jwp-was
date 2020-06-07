package web;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.Set;

public class HttpRequestHandlerComposite implements HttpRequestHandler {

    private Set<HttpRequestHandler> httpRequestHandlers;

    public HttpRequestHandlerComposite(Set<HttpRequestHandler> httpRequestHandlers) {
        this.httpRequestHandlers = httpRequestHandlers;
    }

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        for (HttpRequestHandler httpRequestHandler : this.httpRequestHandlers) {
            if(httpResponse.isResponseDone()) {
                return;
            }
            httpRequestHandler.handleRequest(httpRequest, httpResponse);
        }
    }
}
