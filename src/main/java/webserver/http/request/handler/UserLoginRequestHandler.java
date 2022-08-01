package webserver.http.request.handler;

public class UserLoginRequestHandler implements RequestHandler {
    static final String REQUEST_INDEX = "/user/login";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }
    @Override
    public byte[] execute() {
        return "".getBytes();
    }
}
