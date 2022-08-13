package webserver.http.model.response;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
