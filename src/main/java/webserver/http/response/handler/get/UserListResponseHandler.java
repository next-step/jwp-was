package webserver.http.response.handler.get;

import java.util.Optional;

import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionStorage;

public class UserListResponseHandler implements ResponseHandler {
    private static final String LOGIN_FAIL = "/user/login_failed.html";

    @Override
    public ResponseHeader run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        Optional<HttpSession> session = HttpSessionStorage.getSession(requestHeader.sessionId());

        if (session.isEmpty() || !session.get().getLogin()) {
            return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(LOGIN_FAIL);
        }

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.OK)
                .addContentType(ContentType.HTML)
                .addContentLength(responseBody.length);
    }
}
