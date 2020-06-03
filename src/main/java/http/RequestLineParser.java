package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        String[] protocolValues = values[2].split("/");

        return new RequestLine(values[0], values[1], protocolValues[0], protocolValues[1]);
    }
}
