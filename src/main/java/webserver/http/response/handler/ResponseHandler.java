package webserver.http.response.handler;

import webserver.http.request.header.RequestHeader;

public interface ResponseHandler {
    String run(RequestHeader requestHeader, int lengthOfBodyContent);
}
