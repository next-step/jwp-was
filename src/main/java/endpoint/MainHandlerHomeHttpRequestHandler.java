package endpoint;

import webserver.http.HttpRequestMessage;

public class MainHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/index.html";

    public MainHandlerHomeHttpRequestHandler() {
        super(ENDPOINT);
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {

    }
}
