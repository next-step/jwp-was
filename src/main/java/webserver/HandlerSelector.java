package webserver;

import handler.Handler;
import handler.PathHandler;
import model.request.HttpRequestMessage;

import java.util.List;

public class HandlerSelector {
    private final List<PathHandler> pathHandlers = Handler.getAllHandler();

    public PathHandler selectAvailableHandler(HttpRequestMessage httpRequestMessage) {
        return pathHandlers.stream()
            .filter(it -> it.canHandling(httpRequestMessage))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
    }
}
