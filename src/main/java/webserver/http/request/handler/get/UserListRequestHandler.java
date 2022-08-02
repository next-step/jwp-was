package webserver.http.request.handler.get;

import java.io.IOException;

import model.UserList;
import webserver.http.request.handler.RequestHandler;

public class UserListRequestHandler implements RequestHandler {
    private static final String REQUEST_URI = "/user/list";

    public static String requestUri() {
        return REQUEST_URI;
    }

    @Override
    public byte[] execute() throws IOException {
        UserList userList = new UserList();
        return userList.toUserList().getBytes();
    }
}
