package http;

public class RequestLineParser {

    private final static String REQUEST_LINE_DELIMITER = " ";

    public static RequestLine parse(String value) {
        String[] values = value.split(REQUEST_LINE_DELIMITER);
        return new RequestLine(values[0], new Path(values[1]), new Protocol(values[2]));
    }

}
