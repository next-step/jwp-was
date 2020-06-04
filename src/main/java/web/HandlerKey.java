package web;

import annotations.RequestMapping;
import http.HttpMethod;


import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerKey {
    private String path;
    private HttpMethod httpMethod;

    private HandlerKey(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public static HandlerKey from(Method method) {

        RequestMapping controllerMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        String controllerPath = controllerMapping != null ? controllerMapping.value() : "";

        RequestMapping methodMapping = method.getDeclaredAnnotation(RequestMapping.class);
        String methodPath = methodMapping.value();

        String path = controllerPath + methodPath;
        HttpMethod httpMethod = methodMapping.method();

        return new HandlerKey(path ,httpMethod);
    }

    public static HandlerKey of(String path, HttpMethod httpMethod) {
        return new HandlerKey(path, httpMethod);
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(path, that.path) &&
                httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, httpMethod);
    }
}