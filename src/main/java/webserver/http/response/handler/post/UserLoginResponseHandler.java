package webserver.http.response.handler.post;

import java.util.UUID;

import db.DataBase;
import model.LoginRequest;
import model.LoginUser;
import utils.UserParser;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionStorage;

public class UserLoginResponseHandler implements ResponseHandler {
    private static final String REDIRECT_INDEX_HTML = "/index.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";

    @Override
    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        String protocolVersion = requestHeader.protocolVersion();
        LoginRequest request = loginRequest(requestBody);
        boolean isLogin = isLogin(request);

        if (isLogin) {
            String uuid = setLoginSession(true);

            return new ResponseHeader(protocolVersion, HttpResponseStatus.FOUND)
                    .addContentType(ContentType.HTML)
                    .addLocation(REDIRECT_INDEX_HTML)
                    .addCookieSessionId(uuid)
                    .toString();
        }

        String uuid = setLoginSession(false);
        return new ResponseHeader(protocolVersion, HttpResponseStatus.FOUND)
                .addContentType(ContentType.HTML)
                .addLocation(LOGIN_FAILED_HTML)
                .addCookieSessionId(uuid)
                .toString();
    }

    private String setLoginSession(boolean isLogin) {
        HttpSession httpSession = new HttpSession(UUID.randomUUID());
        httpSession.setLogin(isLogin);
        HttpSessionStorage.setSession(httpSession.getId(), httpSession);
        return httpSession.getId();
    }

    private LoginRequest loginRequest(String requestBody) {
        LoginUser user = UserParser.createLoginUser(requestBody);

        return new LoginRequest(
                user.getUserId(), user.getPassword()
        );
    }

    private boolean isLogin(LoginRequest request) {
        return DataBase.existsUser(request.getUserId()) && matchPassword(request);
    }

    private boolean matchPassword(LoginRequest request) {
        return DataBase.findUserById(request.getUserId()).getPassword().equals(request.getPassword());
    }
}
