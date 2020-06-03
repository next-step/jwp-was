package http;

public class RequestLineParser {
    public static RequestLine parse(final String requestLineStr) {
        String[] values = requestLineStr.split(" ");
        String[] pathAndQueryString = values[1].split("\\?");
        Protocol protocol = new Protocol(values[2]);

        return new RequestLine(values[0], pathAndQueryString[0], pathAndQueryString.length < 2 ? "" : pathAndQueryString[1], protocol);
    }
}
