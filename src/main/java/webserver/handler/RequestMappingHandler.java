package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolver.ViewResolver;

public abstract class RequestMappingHandler implements Handler {

    protected ViewResolver viewResolver;

    protected RequestMappingHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public HttpResponse doHandle(HttpRequest httpRequest) throws Exception {
        if (!canHandle(httpRequest)) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        switch (httpRequest.getMethod()) {
            case GET:
                return doGet(httpRequest);
            case POST:
                return doPost(httpRequest);
            default:
                 throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return getRequestMapping().equals(httpRequest.getPath());
    }

    protected HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected abstract String getRequestMapping();
}