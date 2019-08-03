package webserver.handler;

import webserver.http.request.Request;

public class ConditionHandlerProvider implements HandlerProvider {

    private final Condition condition;
    private final Handler handler;

    ConditionHandlerProvider(final Condition condition,
                             final Handler handler) {
        this.condition = condition;
        this.handler = handler;
    }

    @Override
    public boolean support(final Request request) {
        return condition.support(request);
    }

    @Override
    public Handler provide() {
        return handler;
    }
}
