package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        String protocolAndVersion = values[2];
        return new RequestLine(HttpMethod.valueOf(values[0]), PathAndString.splitPath(values[1]), new Protocol(protocolAndVersion));
    }
}
