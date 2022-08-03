package webserver.http.request.handler.get;

import webserver.http.request.handler.RequestHandler;
import webserver.http.request.header.RequestHeader;

public class UserCreateByQueryParamRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/user/create";
    private static final String EMPTY_BODY = "";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute(RequestHeader requestHeader) {
        return EMPTY_BODY.getBytes();
    }
}
