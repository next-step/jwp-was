package http;

public class RequestLineParser {
    public static RequestLine parse(String s) {
        String[] values = s.split(" ");
        String[] protocolAndVersion = values[2].split("/");
        return new RequestLine(values[0], values[1], new Protocol(protocolAndVersion[1]));
    }

    public static RequestLine parse2(String s) {
        String[] values = s.split(" ");
        return new RequestLine(values[0], values[1], new Protocol(values[2]));
    }
}
