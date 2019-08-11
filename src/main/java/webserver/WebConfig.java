package webserver;

import webserver.http.request.controller.Controller;
import webserver.http.request.controller.ControllerType;
import webserver.http.common.exception.UrlNotFoundException;
import webserver.http.request.handler.RequestHandler;
import webserver.http.request.handler.RequestHandlerType;
import webserver.http.request.handler.RequestMappingHandler;
import webserver.http.response.view.ViewRenderer;
import webserver.http.response.view.ViewType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class WebConfig {

    private static HashMap<Pattern, RequestHandler> handlerCache = new HashMap<>();
    private static HashMap<String, Controller> controllerCache = new HashMap<>();
    private static HashMap<String, ViewRenderer> rendererCache = new HashMap<>();

    static {
        // handler
        for (RequestHandlerType handlerType : RequestHandlerType.values()) {
            handlerCache.put(handlerType.getPattern(), handlerType.getHandler());
        }

        // controller
        for (ControllerType controllerType : ControllerType.values()) {
            controllerCache.put(controllerType.getUrl(), controllerType.getController());
        }

        // renderer
        for (ViewType viewType : ViewType.values()) {
            rendererCache.put(viewType.getPrefix(), viewType.getViewRenderer());
        }
    }

    public static RequestHandler getHandler(String url) {
        return handlerCache.entrySet().stream()
                .filter(entry -> entry.getKey().matcher(url).find())
                .map(Map.Entry::getValue)
                .findFirst().orElse(new RequestMappingHandler("/template"));
    }

    public static Controller getController(String path) {
        return controllerCache.get(path);
    }

    public static ViewRenderer getViewRenderer(String viewName) {
        return rendererCache.entrySet().stream()
                .filter(entry -> viewName.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().orElseThrow(UrlNotFoundException::new);
    }
}
