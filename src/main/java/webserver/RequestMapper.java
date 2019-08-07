package webserver;

import webserver.template.HandleBarViewResolver;
import webserver.template.HtmlViewResolver;
import webserver.template.ViewResolver;
import webserver.view.LoginHandler;
import webserver.view.ResourceHandler;
import webserver.view.UserCreateHandler;
import webserver.view.UserListHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RequestMapper {
    USER_CREATE("/user/create", UserCreateHandler::new, new HtmlViewResolver()),
    USER_LOGIN("/user/login", LoginHandler::new, new HtmlViewResolver()),
    USER_LIST("/user/list", UserListHandler::new, new HandleBarViewResolver()),
    RESOURCE("resource", ResourceHandler::new, new HtmlViewResolver());

    private String path;
    private HandlerCreator handlerCreator;
    private ViewResolver resolver;

    private static Map<String, RequestMapper> uris;
    static {
        uris = Arrays.stream(values())
                .collect(Collectors.toMap(requestMapper -> requestMapper.path, requestMapper -> requestMapper, (requestMapper1, requestMapper2) -> requestMapper1));
    }

    RequestMapper(String path, HandlerCreator creator, ViewResolver resolver) {
        this.path = path;
        this.handlerCreator = creator;
        this.resolver = resolver;
    }

    public static Handler find(String path) {
        return Optional.ofNullable(uris.get(path))
                .map(mapper -> mapper.handlerCreator
                        .createHandler(mapper.resolver))
                .orElse(getResourceHandlerCreator());
    }

    private static Handler getResourceHandlerCreator() {
        RequestMapper mapper = uris.get("resource");
        return mapper.handlerCreator
                .createHandler(mapper.resolver);

    }
}
