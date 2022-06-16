package service;

import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

public abstract class GetService implements Service {

    abstract Response doGet(RequestLine requestLine);

    @Override
    public final Response doService(RequestLine requestLine, RequestBody requestBody) {
        return doGet(requestLine);
    }
}
