package webserver.service;

import webserver.request.header.RequestHeader;
import webserver.response.HttpResponseStatus;
import webserver.response.header.ContentType;
import webserver.response.header.ResponseHeader;
import webserver.response.post.PostUserCreateResponse;
import webserver.response.post.PostUserLoginResponse;
import webserver.service.exception.InvalidRequestException;

public class ResponsePostHandler {
    private static final String USER_CREATE = "/user/create";
    private static final String USER_LOGIN = "/user/login";
    private static final String REDIRECT_INDEX_HTML = "/index.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    private static final String COOKIE_LOGIN_SUCCESS = "logined=true";
    private static final String COOKIE_LOGIN_FAILED = "logined=failed";

    public String handle(RequestHeader header, String requestBody) {
        if (header.index().equals(USER_CREATE)) {
            PostUserCreateResponse response = new PostUserCreateResponse();
            response.response(requestBody);
            ResponseHeader responseHeader = new ResponseHeader(header.protocolVersion(), HttpResponseStatus.FOUND);
            return responseHeader.addContentType(ContentType.HTML).addLocation(REDIRECT_INDEX_HTML).toString();
        }

        if (header.index().equals(USER_LOGIN)) {
            return login(header.protocolVersion(), requestBody).toString();
        }

        throw new InvalidRequestException("제공되지 않은 요청입니다.");
    }

    private ResponseHeader login(String protocolVersion, String requestBody) {
        PostUserLoginResponse response = new PostUserLoginResponse();
        boolean isLogin = response.response(requestBody);

        if (isLogin) {
            return new ResponseHeader(protocolVersion, HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(REDIRECT_INDEX_HTML)
                    .addCookie(COOKIE_LOGIN_SUCCESS);
        }

        return new ResponseHeader(protocolVersion, HttpResponseStatus.FOUND)
                .addContentType(ContentType.HTML)
                .addLocation(LOGIN_FAILED_HTML)
                .addCookie(COOKIE_LOGIN_FAILED);
    }
}
