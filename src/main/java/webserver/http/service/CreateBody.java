package webserver.http.service;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.request.RequestIndex;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.get.GetIndexHtmlResponse;
import webserver.http.response.get.UserList;
import webserver.http.response.header.ContentType;

public class CreateBody {
    public byte[] create(RequestHeader requestHeader) throws IOException, URISyntaxException {
        if (requestHeader.index().equals(RequestIndex.USER_LIST.getPath())) {
            UserList userList = new UserList();
            return userList.toUserList().getBytes();
        }

        GetIndexHtmlResponse response = new GetIndexHtmlResponse();
        return response.response(ContentType.filePath(requestHeader.index()), requestHeader.index());
    }
}
