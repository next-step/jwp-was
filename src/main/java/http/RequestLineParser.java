package http;

public class RequestLineParser {
    public static RequestLine parse(String s) {
        String[] values = s.split(" ");
        return new RequestLine(values[0], values[1], new Protocol(values[2]));
    }
}
