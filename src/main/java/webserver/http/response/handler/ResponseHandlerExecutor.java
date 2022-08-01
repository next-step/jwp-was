package webserver.http.response.handler;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.handler.HomeRequestHandler;
import webserver.http.request.handler.UserCreateRequestHandler;
import webserver.http.request.handler.UserFormRequestHandler;
import webserver.http.request.handler.UserLoginRequestHandler;
import webserver.http.request.header.RequestHeader;

public class ResponseHandlerExecutor {
    private static final Map<String, ResponseHandler> RESPONSE = new HashMap<>();

    static {
        RESPONSE.put(HomeRequestHandler.requestIndex(), new HomeResponseHandler());
        RESPONSE.put(UserFormRequestHandler.requestIndex(), new UserFormResponseHandler());
        RESPONSE.put(UserCreateRequestHandler.requestIndex(), new UserCreateResponseHandler());
        RESPONSE.put(UserLoginRequestHandler.requestIndex(), new UserLoginResponseHandler());
    }

    public String run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        ResponseHandler responseHandler = RESPONSE.getOrDefault(requestHeader.index(), new DefaultResponseHandler());
        return responseHandler.run(requestHeader, requestBody, responseBody);
    }
}
