package utils;

public enum HttpStringType {
    PATH_PATTERN("^/([a-zA-Z0-9]+)([?].+)$"),
    QUERYSTRING_PATTERN("^(.+=.+)+$"),
    DELIMITER_AMPERSAND("&"),
    DELIMITER_EQUAL_SIGN("="),
    DELIMITER_QUESTION_MARK("[?]"),
    DELIMITER_SPACE(" ");

    private String type;

    HttpStringType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
