package http;

public enum MediaType {
    APPLICATION_JSON("application/json"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_CSS("text/css");

    private String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
