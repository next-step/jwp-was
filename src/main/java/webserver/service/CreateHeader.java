package webserver.service;

import static webserver.request.RequestIndex.*;

import java.io.IOException;

import webserver.request.header.RequestHeader;
import webserver.response.HttpResponseStatus;
import webserver.response.get.GetUserFormHtmlResponse;
import webserver.response.header.ContentType;
import webserver.response.header.ResponseHeader;

public class CreateHeader {

    private static final String NOT_SET_COOKIE = "";

    public String create(RequestHeader requestHeader, int lengthOfBodyContent) throws IOException {
        if (requestHeader.index().equals(USER_CREATE.getPath())) {
            GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
            response.response(requestHeader);
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                    .addContentType(ContentType.HTML)
                    .addContentLength(lengthOfBodyContent)
                    .addLocation(GET_INDEX_HTML.getPath())
                    .toString();
        }

        if (requestHeader.index().equals(USER_LIST.getPath())) {
            return userListHeader(requestHeader, lengthOfBodyContent);
        }

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.HTML)
                .addContentLength(lengthOfBodyContent)
                .addLocation(GET_INDEX_HTML.getPath())
                .toString();
    }

    private String userListHeader(RequestHeader requestHeader, int lengthOfBodyContent) {
        String cookie = requestHeader.cookie();
        if (isNotSetCookie(cookie) || !isLogin(cookie)) {
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(LOGIN_FAIL.getPath())
                    .toString();
        }
        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.HTML)
                .addContentLength(lengthOfBodyContent)
                .toString();
    }

    private boolean isLogin(String cookie) {
        return Boolean.parseBoolean(
                cookie.split(" ")[0].split("=")[1]
        );
    }

    private boolean isNotSetCookie(String cookie) {
        return cookie.equals(NOT_SET_COOKIE);
    }
}
