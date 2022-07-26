package endpoint.page;

import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

public class CreateUserPageHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/user/form.html";

    public CreateUserPageHandlerHomeHttpRequestHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        return HttpResponseMessage.page(ENDPOINT);
    }
}
