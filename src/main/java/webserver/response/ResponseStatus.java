package webserver.response;

public enum ResponseStatus {
    SUCCESS(200, "OK"),
    FOUND(302, "Found")
    ;

    private static final String BLANK = " ";
    private final int value;
    private final String reasonParse;

    ResponseStatus(int value, String reasonParse) {
        this.value = value;
        this.reasonParse = reasonParse;
    }

    public String toResponseHeader() {
        return value + BLANK + reasonParse;
    }
}
