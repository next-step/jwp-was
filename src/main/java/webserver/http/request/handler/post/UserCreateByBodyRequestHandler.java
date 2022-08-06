package webserver.http.request.handler.post;

import webserver.http.request.handler.RequestHandler;

public class UserCreateByBodyRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/user/create";
    private static final String EMPTY_BODY = "";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute() {
        return EMPTY_BODY.getBytes();
    }
}
