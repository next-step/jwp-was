package endpoint;

import webserver.http.HttpRequestMessage;

public interface HttpRequestHandler {
    void handle(HttpRequestMessage httpRequestMessage);
}
