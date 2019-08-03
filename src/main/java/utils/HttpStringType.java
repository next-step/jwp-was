package utils;

public enum HttpStringType {
    QUERYSTRING_PATTERN("^/([a-zA-Z0-9]+)[?]([a-zA-Z0-9]+=.+)+$"),
    REQUESTLINE_DELIMITER(" "),
    QUERYSTRING_DELIMITER("[?]"),
    PARAMETER_DELIMITER("&"),
    VALUE_DELIMITER("=");

    private String type;

    HttpStringType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
