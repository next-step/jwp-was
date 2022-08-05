package handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Handler {
    INDEX_HANDLER(new IndexHandler()),
    USER_HANDLER(new UserHandler()),
    RESOURCE_HANDLER(new StaticResourceHandler());

    private final PathHandler pathHandler;

    Handler(PathHandler pathHandler) {
        this.pathHandler = pathHandler;
    }

    public static List<PathHandler> getAllHandler() {

        return Arrays.stream(Handler.values()).map(it -> it.pathHandler).collect(Collectors.toList());
    }
}
