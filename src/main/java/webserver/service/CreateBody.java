package webserver.service;

import java.io.IOException;

import webserver.request.RequestIndex;
import webserver.request.header.RequestHeader;
import webserver.response.get.GetIndexHtmlResponse;
import webserver.response.get.UserList;

public class CreateBody {
    public byte[] create(RequestHeader requestHeader) throws IOException {
        if (requestHeader.index().equals(RequestIndex.USER_LIST.getPath())) {
            UserList userList = new UserList();
            return userList.toUserList().getBytes();
        }

        GetIndexHtmlResponse response = new GetIndexHtmlResponse();
        return response.response(requestHeader.index());
    }
}
