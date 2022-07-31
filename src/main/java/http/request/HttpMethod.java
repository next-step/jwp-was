package http.request;

public enum HttpMethod {
    GET, POST;

    public boolean isGet() {
        return this == GET;
    }
}
