package webserver.http.handler;

import webserver.http.HttpRequest;

public class PatternMatchHandlerProvider implements HandlerProvider {

    private final String pathPattern;
    private final Handler handler;

    public PatternMatchHandlerProvider(String pathPattern, Handler handler) {
        this.pathPattern = pathPattern;
        this.handler = handler;
    }

    @Override
    public boolean support(final HttpRequest request) {
        return request.matchPath(pathPattern);
    }

    @Override
    public Handler provide() {
        return handler;
    }
}
