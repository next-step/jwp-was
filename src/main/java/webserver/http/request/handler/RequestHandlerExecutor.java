package webserver.http.request.handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.handler.exception.InvalidRequestException;
import webserver.http.request.handler.get.DefaultRequestHandler;
import webserver.http.request.handler.get.HomeRequestHandler;
import webserver.http.request.handler.get.UserFormRequestHandler;
import webserver.http.request.handler.get.UserListRequestHandler;
import webserver.http.request.header.RequestHeader;

public class RequestHandlerExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerExecutor.class);
    private static final Map<String, RequestHandler> REQUEST = new HashMap<>();

    static {
        REQUEST.put(HomeRequestHandler.requestUri(), new HomeRequestHandler());
        REQUEST.put(UserFormRequestHandler.requestUri(), new UserFormRequestHandler());
        REQUEST.put(UserListRequestHandler.requestUri(), new UserListRequestHandler());
    }

    public byte[] run(RequestHeader requestHeader) {
        try {
            RequestHandler requestHandler = REQUEST.getOrDefault(requestHeader.uri(), new DefaultRequestHandler(requestHeader));
            return requestHandler.execute();
        } catch (IOException | URISyntaxException e) {
            logger.error("http request error uri={}", requestHeader.uri(), e);
        }
        throw new InvalidRequestException("지원하지 않는 요청입니다.");
    }
}
