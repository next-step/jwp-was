package webserver.http.handler;

public class PatternMatchHandlerProvider extends ConditionHandlerProvider {

    public PatternMatchHandlerProvider(final String pathPattern,
                                       final Handler handler) {
        this(request -> request.matchPath(pathPattern), handler);
    }

    public PatternMatchHandlerProvider(final Condition condition,
                                       final Handler handler) {
        super(condition, handler);
    }
}
