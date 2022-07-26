package webserver.http;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
