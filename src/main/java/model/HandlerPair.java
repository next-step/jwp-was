package model;

import java.lang.reflect.Method;

public class HandlerPair {

    private Object controller;
    private Method handler;

    public HandlerPair() {
    }

    public HandlerPair(Object controller, Method handler) {
        this.controller = controller;
        this.handler = handler;
    }

    public Object getController() {
        return controller;
    }

    public Method getHandler() {
        return handler;
    }

}
