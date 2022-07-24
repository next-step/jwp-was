package endpoint;

import webserver.http.request.HttpRequestMessage;

public interface HttpRequestHandler {
    void handle(HttpRequestMessage httpRequestMessage);
}
