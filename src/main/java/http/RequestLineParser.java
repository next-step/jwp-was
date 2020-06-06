package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        String[] protocolAndVersion = values[2].split("/");
        return new RequestLine(values[0], values[1], protocolAndVersion[0], protocolAndVersion[1]);
    }
}
