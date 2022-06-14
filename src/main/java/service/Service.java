package service;

import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

public interface Service {

    Response doService(RequestLine requestLine, RequestBody requestBody);

    boolean canServe(RequestLine requestLine);
}
