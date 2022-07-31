package webserver.http.request.requestline;

public enum Method {
    GET, POST, PUT, DELETE, PATCH;

    public boolean isMethodEqual(Method method) {
        return method == this;
    }
}
