package webserver.http.response.handler.get;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class UserListResponseHandler implements ResponseHandler {
    private static final String NOT_SET_COOKIE = "";
    private static final String LOGIN_FAIL = "/user/login_failed.html";

    @Override
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        String cookie = requestHeader.cookie();
        if (isNotSetCookie(cookie) || !isLogin(cookie)) {
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(LOGIN_FAIL)
                    .toString();
        }
        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.HTML)
                .addContentLength(responseBody.length)
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
