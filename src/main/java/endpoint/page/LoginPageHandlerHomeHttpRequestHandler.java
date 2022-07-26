package endpoint.page;

import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

public class LoginPageHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/user/login.html";

    public LoginPageHandlerHomeHttpRequestHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        return HttpResponseMessage.page(ENDPOINT);
    }
}
