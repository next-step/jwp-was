package http;

public enum ContentType {
    TEXT_HTML("text/html;charset=utf-8"),
    ALL("*/*");

    private final String contentType;

    ContentType(final String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
