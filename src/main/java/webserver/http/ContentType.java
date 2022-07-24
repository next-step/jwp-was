package webserver.http;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "*/*");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public String getExtension() {
        return extension;
    }

    public String getValue() {
        return value;
    }
}
