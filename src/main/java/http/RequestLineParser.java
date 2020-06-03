package http;

public class RequestLineParser {
    public static RequestLine parse(final String requestLineStr) {
        String[] values = requestLineStr.split(" ");
        String[] pathAndQueryString = values[1].split("/?");
        String[] protocolAndVersion = values[2].split("/");

        return new RequestLine(values[0], values[1], protocolAndVersion[0], protocolAndVersion[1]);
    }
}
