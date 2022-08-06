package webserver.http.response.handler;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.header.ResponseHeader;

public interface ResponseHandler {
    ResponseHeader run(RequestHeader requestHeader, String requestBody, byte[] responseBody);
}
