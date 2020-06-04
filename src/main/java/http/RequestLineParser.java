package http;

public class RequestLineParser {
    public static RequestLine parse(final String requestLineStr) {
        String[] values = requestLineStr.split(" ");
        Path path = new Path(values[1]);
        Protocol protocol = new Protocol(values[2]);

        return new RequestLine(values[0], path, protocol);
    }
}
