package webserver;

import handler.AbstractHandler;
import handler.Handler;
import model.request.HttpRequestMessage;

import java.util.List;

public class HandlerSelector {
    private final List<AbstractHandler> pathHandlers = Handler.getAllHandler();

    public AbstractHandler selectAvailableHandler(HttpRequestMessage httpRequestMessage) {
        return pathHandlers.stream()
            .filter(it -> it.canHandling(httpRequestMessage))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
    }
}
