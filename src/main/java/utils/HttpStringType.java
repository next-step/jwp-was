package utils;

public enum HttpStringType {
    QUERYSTRING_PATTERN("^([^=&]+=[^=&]+)+(&[^&]+=[^=&]+)*$"),

    DELIMITER_AMPERSAND("&"),
    DELIMITER_EQUAL_SIGN("="),
    DELIMITER_QUESTION_MARK("[?]"),
    DELIMITER_SPACE(" "),

    TEMPLATES_PREFIX("./templates"),
    STATIC_PREFIX("./static"),

    FILE_PATH_EXT(".html");

    private String type;

    HttpStringType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
