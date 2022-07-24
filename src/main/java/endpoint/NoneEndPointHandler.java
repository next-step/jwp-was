package endpoint;

import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;

public class NoneEndPointHandler extends HttpRequestEndpointHandler {

    public NoneEndPointHandler() {
        super(new Endpoint(HttpMethod.GET, ""));
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {
    }
}
