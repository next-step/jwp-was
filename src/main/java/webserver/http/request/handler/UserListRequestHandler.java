package webserver.http.request.handler;

import java.io.IOException;

import model.UserList;

public class UserListRequestHandler implements RequestHandler {
    static final String REQUEST_INDEX = "/user/list";

    public static String requestIndex() {
        return REQUEST_INDEX;
    }
    @Override
    public byte[] execute() throws IOException {
        UserList userList = new UserList();
        return userList.toUserList().getBytes();
    }
}
