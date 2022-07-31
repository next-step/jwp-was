package webserver.http.response.handler;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.handler.HomeRequestHandler;
import webserver.http.request.header.RequestHeader;

public class ResponseHandlerExecutor {
    private static final Map<String, ResponseHandler> RESPONSE_HANDLER_MAP = new HashMap<>();

    static {
        RESPONSE_HANDLER_MAP.put(HomeRequestHandler.requestIndex(), new HomeResponseHandler());
    }

    public String run(RequestHeader requestHeader, int lengthOfBodyContent) {
        ResponseHandler responseHandler = RESPONSE_HANDLER_MAP.getOrDefault(requestHeader.index(), new DefaultResponseHandler());
        return responseHandler.run(requestHeader, lengthOfBodyContent);
    }
}
