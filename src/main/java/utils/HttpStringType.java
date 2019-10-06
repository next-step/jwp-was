package utils;

public enum HttpStringType {
    QUERYSTRING_PATTERN("^([^=&]+=[^=&]+)+(&[^&]+=[^=&]+)*$"),

    DLM_AND("&"),
    DLM_EQUAL("="),
    DLM_QUESTION("[?]"),
    DLM_SPACE(" "),
    DLM_SEMICOLON(";"),

    PFX_TEMPLATES("./templates"),
    PFX_STATIC("./static"),

    FILE_PATH_EXT(".html"),

    SESSION_ID("SESSION_ID");

    private String type;

    HttpStringType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
