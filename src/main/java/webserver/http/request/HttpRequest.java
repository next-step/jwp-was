package webserver.http.request;

import webserver.http.request.handler.RequestHandlerExecutor;
import webserver.http.request.header.RequestHeader;

public class HttpRequest {
    public byte[] run(RequestHeader requestHeader) {
        RequestHandlerExecutor handlerExecutor = new RequestHandlerExecutor();
        return handlerExecutor.run(requestHeader);
    }
}
