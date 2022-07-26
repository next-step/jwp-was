package endpoint;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

public class NoneEndPointHandler extends HttpRequestEndpointHandler {

    public NoneEndPointHandler() {
        super(new Endpoint(HttpMethod.GET, ""));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        return HttpResponseMessage.notFound();
    }
}
