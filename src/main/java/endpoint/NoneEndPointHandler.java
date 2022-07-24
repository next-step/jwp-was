package endpoint;

import webserver.http.HttpRequestMessage;

public class NoneEndPointHandler extends HttpRequestEndpointHandler {

    public NoneEndPointHandler() {
        super("");
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {
    }
}
