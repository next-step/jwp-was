package http.handler.mapper;

import http.common.HttpStatus;
import http.handler.ExceptionHandler;
import http.handler.Handler;
import http.handler.StaticResourceHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

import static http.handler.mapper.Handlers.*;
import static java.util.stream.Collectors.toMap;

@Slf4j
public enum HandlerMapper {
    CREATE_USER("/createUser", CREATE_USER_HANDLER),
    LIST_USER("/user/list", LIST_USER_HANDLER),
    LOGIN("/loginUser", LOGIN_HANDLER),
    ;

    private static final Map<String, Handler> requestMap = initRequestMap();

    @Getter
    private String url;

    @Getter
    private Handler handler;

    HandlerMapper(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    private static Map<String, Handler> initRequestMap() {
        return Arrays.stream(values())
            .collect(toMap(HandlerMapper::getUrl, HandlerMapper::getHandler));
    }

    public static Handler getHandler(String url) {
        if (StaticResource.matches(url)) {
            return new StaticResourceHandler(url, StaticResource.getStaticResource(url));
        }

        if (requestMap.containsKey(url)) {
            return requestMap.get(url);
        }

        return new ExceptionHandler(HttpStatus.NOT_FOUND);
    }
}
