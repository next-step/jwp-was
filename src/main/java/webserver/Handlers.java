package webserver;

import http.request.Request;

import java.util.HashSet;
import java.util.Set;

public class Handlers {
    private static Set<Handler> handlers = new HashSet<>();

    public static void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public static Handler findHandler(Request request) {
        return handlers.stream()
                .filter(handler -> handler.isSameUrl(request.getUrl()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found matched RequestHandler!"));
    }
}
