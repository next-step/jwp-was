package webserver.http.response;

import webserver.http.request.header.RequestHeader;

public interface ResponseHandler {
    void run(RequestHeader requestHeader, byte[] body);
}
