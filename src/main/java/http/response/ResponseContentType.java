package http.response;

public enum ResponseContentType {

    HTML("text/html"),
    CSS("text/css");

    private String type;

    ResponseContentType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + ";charset=utf-8";
    }
}
