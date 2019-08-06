package webserver.handler;

import webserver.http.HttpMethod;
import webserver.http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper implements HandlerProvider {

    private static final Handler NOT_FOUND = (ignore, response) -> response.notFound();

    private final Map<Condition, Handler> handlerMapper = new HashMap<>();

    public void register(final String path,
                         final HttpMethod httpMethod,
                         final Handler handler) {
        handlerMapper.put(createCondition(path, httpMethod), handler);
    }

    public Handler provide(final Request request) {
        return handlerMapper.entrySet()
                .stream()
                .filter(entry -> entry.getKey().support(request))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(NOT_FOUND);
    }

    private Condition createCondition(final String path,
                                      final HttpMethod httpMethod) {
        return request -> request.matchPath(path) && request.matchMethod(httpMethod);
    }
}
