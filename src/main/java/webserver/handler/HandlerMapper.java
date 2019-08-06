package webserver.handler;

import webserver.http.RequestMethod;
import webserver.http.request.Request;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper implements HandlerProvider {

    private static final Handler NOT_FOUND = (ignore, response) -> response.notFound();

    private final Map<Condition, Handler> handlerMapper = new HashMap<>();

    public void register(final String path,
                         final RequestMethod requestMethod,
                         final Handler handler) {
        handlerMapper.put(createCondition(path, requestMethod), handler);
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
                                      final RequestMethod requestMethod) {
        return request -> request.matchPath(path) && request.matchMethod(requestMethod);
    }
}
