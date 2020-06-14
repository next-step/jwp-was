package webserver;

import http.RequestMessage;
import webserver.handler.DefaultHandler;
import webserver.handler.Handler;
import webserver.handler.UserCreateHandler;
import webserver.handler.UserLoginHandler;


import java.util.Arrays;

public enum HandlerMapper {

    USER_CREATE("/user/create", UserCreateHandler.getInstance()),
    USER_LOGIN("/user/login", UserLoginHandler.getInstance()),
    DEFAULT("/", DefaultHandler.getInstance());

    private final String mappingPath;
    private final Handler handlerInstance;

    HandlerMapper(String mappingPath, Handler handlerInstance) {
        this.mappingPath = mappingPath;
        this.handlerInstance = handlerInstance;
    }

    public static Handler getHandler(RequestMessage requestMessage) {
        String requestPath = requestMessage.getPath();
        return Arrays.stream(values())
                .filter(pair -> pair.mappingPath.startsWith(requestPath))
                .findFirst()
                .orElseGet(() -> DEFAULT).handlerInstance;
    }
}
