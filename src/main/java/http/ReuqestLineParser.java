package http;

public class ReuqestLineParser {
    public static RequestLine parse(String request) {
        final String[] values = request.split(" ");
        return new RequestLine(values[0], values[1], new Protocol(values[2]));
    }
}
