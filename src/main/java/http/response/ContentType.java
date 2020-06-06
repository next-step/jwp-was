package http.response;

public enum ContentType {
    CSS("text/css"),
    HTML("text/html");

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }
}
