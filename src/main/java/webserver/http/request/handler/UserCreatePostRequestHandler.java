package webserver.http.request.handler;

public class UserCreatePostRequestHandler implements RequestHandler{
    static final String REQUEST_INDEX = "/user/create";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }

    @Override
    public byte[] execute() {
        return "".getBytes();
    }
}
