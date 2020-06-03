package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        final String[] splited = requestLine.split(" ");
        final String[] protocolAndVersion = splited[2].split("/");
        return new RequestLine(splited[0], splited[1], protocolAndVersion[0], protocolAndVersion[1]);
    }
}
