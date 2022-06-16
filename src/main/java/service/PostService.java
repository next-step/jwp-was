package service;

import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

public abstract class PostService implements Service {

    abstract Response doPost(RequestLine requestLine, RequestBody requestBody);

    @Override
    public final Response doService(RequestLine requestLine, RequestBody requestBody) {
        return doPost(requestLine, requestBody);
    }
}
