package webserver;

import webserver.view.LoginHandler;
import webserver.view.ResourceHandler;
import webserver.view.UserCreateHandler;
import webserver.view.UserListHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RequestMapper {
    USER_CREATE("/user/create", UserCreateHandler::new),
    USER_LOGIN("/user/login", LoginHandler::new),
    USER_LIST("/user/list", UserListHandler::new),
    RESOURCE("resource", ResourceHandler::new);

    private String path;
    private HandlerCreator handlerCreator;

    private static Map<String, RequestMapper> uris;
    static {
        uris = Arrays.stream(values())
                .collect(Collectors.toMap(requestMapper -> requestMapper.path, requestMapper -> requestMapper, (requestMapper1, requestMapper2) -> requestMapper1));
    }

    RequestMapper(String path, HandlerCreator creator) {
        this.path = path;
        this.handlerCreator = creator;
    }

    public static Handler find(String path) {
        return Optional.ofNullable(uris.get(path))
                .map(mapper -> mapper.handlerCreator)
                .map(HandlerCreator::createHandler)
                .orElse(getResourceHandlerCreator().createHandler());
    }

    private static HandlerCreator getResourceHandlerCreator() {
        return uris.get("resource")
                .handlerCreator;
    }
}
