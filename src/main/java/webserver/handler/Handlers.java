package webserver.handler;

import http.request.Request;
import http.response.resources.StaticResources;
import http.response.resources.TemplateResources;
import webserver.handler.custom.StaticResourceHandler;
import webserver.handler.custom.TemplateResourceHandler;

import java.util.HashSet;
import java.util.Set;

public class Handlers {
    private static Set<Handler> handlers = new HashSet<>();

    public static void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public static Handler findHandler(Request request) {
        String url = request.getUrl();

        if (StaticResources.match(url)) {
            return new StaticResourceHandler();
        }

        if (TemplateResources.match(url)) {
            return new TemplateResourceHandler();
        }

        return handlers.stream()
                .filter(handler -> handler.isSameUrl(request.getUrl()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not found matched RequestHandler!"));
    }
}
