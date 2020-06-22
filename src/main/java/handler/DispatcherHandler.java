package handler;

import http.request.RequestMessage;
import http.response.ResponseMessage;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherHandler implements Handler {

    private static final DispatcherHandler INSTANCE = new DispatcherHandler();
    private static final Map<String, Handler> handlerMapper = new HashMap<>();

    static {
        handlerMapper.put("/", DefaultHandler.getInstance());
        handlerMapper.put("/user/create", UserCreateHandler.getInstance());
        handlerMapper.put("/user/login", UserLoginHandler.getInstance());
        handlerMapper.put("/user/list", UserListHandler.getInstance());
        handlerMapper.put("/user/logout", UserLogoutHandler.getInstance());
    }

    private DispatcherHandler () {
    }

    public static DispatcherHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void service(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        Handler handler = getHandler(requestMessage);
        handler.service(requestMessage, responseMessage);
    }

    public Handler getHandler(RequestMessage requestMessage) {
        String requestPath = requestMessage.getPath();
        return handlerMapper.entrySet().stream()
                .filter(e -> requestPath.equals(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(DefaultHandler::getInstance);
    }
}
