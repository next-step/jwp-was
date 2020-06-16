package http;

public enum RequestHeaderLineType {
    FIRST_LINE("Frist line"),
    CONTENT_LENGTH_LINE("Content length line"),
    CONTENT_TYPE_LINE("Content type line"),
    EMPTY_LINE("Empty line"),
    OTHER_LINE("Other kind line"),
    DO_NOT_PARSE_LINE("do not parse line")
    ;

    private String name;

    RequestHeaderLineType(String name) {
        this.name = name;
    }

    public static RequestHeaderLineType parse(String requestHeaderLine) {
        if (requestHeaderLine == null)
            return RequestHeaderLineType.DO_NOT_PARSE_LINE;
        if (requestHeaderLine.isEmpty())
            return RequestHeaderLineType.EMPTY_LINE;
        String[] values = requestHeaderLine.split(" ");
        if (values.length == 3 && HttpMethod.of(values[0]) != null)
            return RequestHeaderLineType.FIRST_LINE;
        if (values.length == 2 && RequestHeaderLine.CONTENT_LENGTH_KEY.equals(values[0]))
            return RequestHeaderLineType.CONTENT_LENGTH_LINE;
        if (values.length == 2 && RequestHeaderLine.CONTENT_TYPE_KEY.equals(values[0]))
            return RequestHeaderLineType.CONTENT_TYPE_LINE;

        return RequestHeaderLineType.OTHER_LINE;
    }

    public String getName() {
        return name;
    }
}
