package endpoint.staticresource;

import endpoint.HttpRequestHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

public class StaticResourceRequestHandler implements HttpRequestHandler {

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        return HttpResponseMessage.staticResource(httpRequestMessage.httpPath());
    }
}
