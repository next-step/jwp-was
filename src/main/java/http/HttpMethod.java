package http;

public enum HttpMethod {
    GET,
    POST;

    public boolean canSupportBody() {
        return this.equals(POST);
    }
}
