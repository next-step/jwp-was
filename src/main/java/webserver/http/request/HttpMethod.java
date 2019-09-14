package webserver.http.request;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public boolean match(String httpMethod) {
        return this.name().equals(httpMethod);
    }
}
