package webserver;

import http.request.Request;
import webserver.customhandler.StaticResourceHandler;
import webserver.customhandler.TemplateHandler;
import webserver.resources.StaticResources;
import webserver.resources.TemplateResources;

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
            return new TemplateHandler();
        }

        return handlers.stream()
                .filter(handler -> handler.isSameUrl(request.getUrl()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found matched RequestHandler!"));
    }
}
