package webserver.http.request.handler;

public class UserListRequestHandler implements RequestHandler {
    static final String REQUEST_INDEX = "/user/list";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }
    @Override
    public byte[] execute() {
        return "".getBytes();
    }
}
