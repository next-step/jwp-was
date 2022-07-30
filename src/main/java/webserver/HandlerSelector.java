package webserver;

import handler.Handler;
import handler.PathHandler;
import model.HttpRequestHeader;

import java.util.List;

public class HandlerSelector {
    private final List<PathHandler> pathHandlers = Handler.getAllHandler();

    public PathHandler selectAvailableHandler(HttpRequestHeader httpRequestHeader) {
        return pathHandlers.stream()
            .filter(it -> it.canHandling(httpRequestHeader))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
    }
}
