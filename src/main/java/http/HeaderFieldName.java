package http;

public enum HeaderFieldName {
    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    COOKIE("Cookie"),
    ;

    private String value;

    HeaderFieldName(String value) {
        this.value = value;
    }

    public String stringify() {
        return this.value;
    }

}
