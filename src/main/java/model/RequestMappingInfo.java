package model;

import controller.Controller;

import java.util.Objects;

public class RequestMappingInfo {

    private HttpMethod method;
    private String path;
    private Controller controller;

    public RequestMappingInfo(HttpMethod method, String path, Controller controller) {
        this.method = method;
        this.path = path;
        this.controller = controller;
    }

    public RequestMappingInfo(HttpRequest request) {
        this.method = request.getMethod();
        this.path = request.getPath();
    }

    public boolean match(RequestMappingInfo info) {
        return method == info.getMethod() && info.getPath().matches(path);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMappingInfo that = (RequestMappingInfo) o;
        return method == that.method && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }

    public Controller getController() {
        return controller;
    }
}
