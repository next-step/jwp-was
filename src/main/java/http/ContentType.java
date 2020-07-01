package http;

public enum ContentType {
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    ALL("*/*");

    private final String contentType;

    ContentType(final String contentType) {
        this.contentType = contentType;
    }

    public static String getContentType(String contentType) {
        return contentType.split(",")[0];
    }
}
