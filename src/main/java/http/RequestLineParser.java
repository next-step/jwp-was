package http;

public class RequestLineParser {
    private static final String BLANK = " ";

    public static RequestLine parse(String requestLines) {
        String[] values = requestLines.split(BLANK);
        return RequestLine.of(values[0], values[1], values[2]);
    }
}
