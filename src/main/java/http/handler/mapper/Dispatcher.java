package http.handler.mapper;

import com.google.common.collect.Maps;
import http.common.HttpStatus;
import http.handler.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class Dispatcher {
    @Getter
    private static final Map<String, Handler> handlers = Maps.newHashMap();

    public static String CREATE_USER_URL = "/createUser";
    public static String LIST_USER_URL = "/user/list";
    public static String LOGIN_USER_URL = "/loginUser";

    static {
        handlers.put(CREATE_USER_URL, new CreateUserHandler());
        handlers.put(LIST_USER_URL, new ListUserHandler());
        handlers.put(LOGIN_USER_URL, new LoginHandler());

        handlers.forEach((uri, handler) -> log.debug("uri: {}, handler: {}", uri, handler.getClass().getSimpleName()));
    }

    public static void dispatch(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        log.debug("httpRequest: {}", StringUtils.toPrettyJson(request));

        Handler handler = getHandler(request.getPath());
        handler.handle(request, response);

        log.debug("httpResponse: {}", StringUtils.toPrettyJson(response));
    }

    public static Handler getHandler(String url) {
        if (StaticResource.matches(url)) {
            return new StaticResourceHandler(url, StaticResource.getStaticResource(url));
        }

        if (handlers.containsKey(url)) {
            return handlers.get(url);
        }

        return new ExceptionHandler(HttpStatus.NOT_FOUND);
    }
}
