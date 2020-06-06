package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        String[] pathAndString = values[1].split("\\?");
        String protocolAndVersion = values[2];
        return new RequestLine(values[0], pathAndString[0], pathAndString[1], new Protocol(protocolAndVersion));
    }
}
