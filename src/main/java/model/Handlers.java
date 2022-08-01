package model;

import java.lang.reflect.Method;
import java.util.List;

public class Handlers {

    private final List<Method> handlers;

    public Handlers(List<Method> handlers) {
        this.handlers = handlers;
    }

    public List<Method> getHandlers() {
        return handlers;
    }
}
