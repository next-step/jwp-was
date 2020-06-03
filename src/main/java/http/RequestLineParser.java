package http;

public class RequestLineParser {

    public static RequestLine parse(String value) {
        String[] values = value.split(" ");
        String[] protocolAndVersion = values[2].split("/");

        return new RequestLine(values[0], values[1], protocolAndVersion[0], protocolAndVersion[1]);
    }

    public static QueryString parseQueryString(String value) {
        return new QueryString(value);
    }
}
