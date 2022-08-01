package webserver.http.request.handler.post;

import webserver.http.request.handler.RequestHandler;

public class UserCreatePostRequestHandler implements RequestHandler {
    private static final String REQUEST_INDEX = "/user/create";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }

    @Override
    public byte[] execute() {
        return "".getBytes();
    }
}
