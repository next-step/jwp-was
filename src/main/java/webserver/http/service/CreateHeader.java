package webserver.http.service;

import java.io.IOException;

import webserver.http.request.RequestIndex;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.get.GetUserFormHtmlResponse;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class CreateHeader {

    private static final String NOT_SET_COOKIE = "";

    public String create(RequestHeader requestHeader, int lengthOfBodyContent) throws IOException {
        if (requestHeader.index().equals(RequestIndex.USER_CREATE.getPath())) {
            GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
            response.response(requestHeader);
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                    .addContentType(ContentType.HTML)
                    .addContentLength(lengthOfBodyContent)
                    .addLocation(requestHeader.index())
                    .toString();
        }

        if (requestHeader.index().equals(RequestIndex.USER_LIST.getPath())) {
            return userListHeader(requestHeader, lengthOfBodyContent);
        }

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.response(requestHeader.index()))
                .addContentLength(lengthOfBodyContent)
                .addLocation(RequestIndex.GET_INDEX_HTML.getPath())
                .toString();
    }

    private String userListHeader(RequestHeader requestHeader, int lengthOfBodyContent) {
        String cookie = requestHeader.cookie();
        if (isNotSetCookie(cookie) || !isLogin(cookie)) {
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(RequestIndex.LOGIN_FAIL.getPath())
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
