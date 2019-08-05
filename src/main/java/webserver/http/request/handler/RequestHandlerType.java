package webserver.http.request.handler;

import java.util.regex.Pattern;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public enum RequestHandlerType {

    LOGIN("([^\\s]+(\\/login)$)", DefaultRequestHandler::new),
    TEMPLATE_RESOURCE("([^\\s]+(\\.(?i)(html|ico))$)", TemplateRequestHandler::new),
    STATIC_RESOURCE("([^\\s]+(\\.(?i)(jpg|gif|png|css|js))$)", DefaultRequestHandler::new),
    ;

    Pattern pattern;
    HandlerCreator handlerCreator;

    RequestHandlerType(String regex, HandlerCreator handlerCreator) {
        this.pattern = Pattern.compile(regex);
        this.handlerCreator = handlerCreator;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public RequestHandler getHandler() {
        return handlerCreator.getHandler();
    }

    @FunctionalInterface
    public interface HandlerCreator {
        RequestHandler getHandler();
    }
}
