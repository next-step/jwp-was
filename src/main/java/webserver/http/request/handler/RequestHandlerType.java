package webserver.http.request.handler;

import java.util.regex.Pattern;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public enum RequestHandlerType {

    TEMPLATE_RESOURCE("([^\\s]+(\\.(?i)(html))$)", "/templates", StaticResourceRequestHandler::new),
    STATIC_RESOURCE("([^\\s]+(\\.(?i)(jpg|gif|png|css|js|ico|woff|ttf))$)", "/static", StaticResourceRequestHandler::new),
    ;

    private final String basePath;
    private final Pattern pattern;
    private final HandlerCreator handlerCreator;

    RequestHandlerType(String regex, String basePath, HandlerCreator handlerCreator) {
        this.pattern = Pattern.compile(regex);
        this.basePath = basePath;
        this.handlerCreator = handlerCreator;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public RequestHandler getHandler() {
        return handlerCreator.createHandler(this.basePath);
    }

    @FunctionalInterface
    public interface HandlerCreator {
        RequestHandler createHandler(String prefix);
    }
}
