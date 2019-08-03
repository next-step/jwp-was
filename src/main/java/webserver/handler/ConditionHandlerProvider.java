package webserver.handler;

import webserver.http.request.HttpRequest;

public class ConditionHandlerProvider implements HandlerProvider {

    private final Condition condition;
    private final Handler handler;

    public ConditionHandlerProvider(final Condition condition,
                                    final Handler handler) {
        this.condition = condition;
        this.handler = handler;
    }

    @Override
    public boolean support(final HttpRequest request) {
        return condition.support(request);
    }

    @Override
    public Handler provide() {
        return handler;
    }
}
