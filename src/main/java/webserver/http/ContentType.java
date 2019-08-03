package webserver.http;

public enum ContentType {

    TEXT_HTML_UTF_8("text/html;charset=utf-8");

    private final String contentType;

    ContentType(final String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return contentType;
    }
}
