package webserver.http.response;

import java.io.IOException;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.handler.ResponseHandlerExecutor;

public class HttpResponse {
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) throws IOException {
        ResponseHandlerExecutor responseHandlerExecutor = new ResponseHandlerExecutor();
        return responseHandlerExecutor.run(requestHeader, requestBody, responseBody);
    }
}