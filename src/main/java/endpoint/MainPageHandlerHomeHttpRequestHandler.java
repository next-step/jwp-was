package endpoint;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

public class MainPageHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/index.html";

    public MainPageHandlerHomeHttpRequestHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        return HttpResponseMessage.page(ENDPOINT);
    }
}
