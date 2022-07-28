package endpoint;

import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

public interface HttpRequestHandler {
    HttpResponseMessage handle(HttpRequestMessage httpRequestMessage);
}
