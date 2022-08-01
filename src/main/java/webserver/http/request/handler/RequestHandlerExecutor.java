package webserver.http.request.handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.header.RequestHeader;
import webserver.http.service.exception.InvalidRequestException;

public class RequestHandlerExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerExecutor.class);
    private static final Map<String, RequestHandler> REQUEST = new HashMap<>();

    static {
        REQUEST.put(HomeRequestHandler.REQUEST_INDEX, new HomeRequestHandler());
        REQUEST.put(UserFormRequestHandler.REQUEST_INDEX, new UserFormRequestHandler());
    }

    public byte[] run(RequestHeader requestHeader) {
        RequestHandler requestHandler = REQUEST.get(requestHeader.index());
        try {
            if (null != requestHandler) {
                return requestHandler.execute();
            }
            DefaultRequestHandler defaultRequestHandler = new DefaultRequestHandler();
            return defaultRequestHandler.execute(requestHeader);
        } catch (IOException | URISyntaxException e) {
            logger.error("http request error index={}", requestHeader.index(), e);
        }
        throw new InvalidRequestException("지원하지 않는 요청입니다.");
    }
}
