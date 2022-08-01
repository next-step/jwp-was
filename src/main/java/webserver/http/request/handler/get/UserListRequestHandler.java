package webserver.http.request.handler.get;

import java.io.IOException;

import model.UserList;
import webserver.http.request.handler.RequestHandler;

public class UserListRequestHandler implements RequestHandler {
    private static final String REQUEST_INDEX = "/user/list";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }

    @Override
    public byte[] execute() throws IOException {
        UserList userList = new UserList();
        return userList.toUserList().getBytes();
    }
}
