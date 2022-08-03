package webserver.http.request.handler.get;

import java.io.IOException;

import model.UserList;
import webserver.http.request.handler.RequestHandler;
import webserver.http.request.header.RequestHeader;

public class UserListRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/user/list";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute(RequestHeader requestHeader) throws IOException {
        UserList userList = new UserList();
        return userList.toUserList().getBytes();
    }
}
