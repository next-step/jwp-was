package model;

import types.HttpMethod;

import java.lang.reflect.Method;

public class HandlerData {

    private final Object controller;
    private final Method handler;
    private final HttpMethod httpMethod;

    public HandlerData(Object controller, Method handler, HttpMethod httpMethod) {
        this.controller = controller;
        this.handler = handler;
        this.httpMethod = httpMethod;
    }

    public Object getController() {
        return controller;
    }

    public Method getHandler() {
        return handler;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
