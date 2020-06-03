package http;

public class RequestLineParser {

    public static RequestLine parse(String requestLineStr) {
        String[] value = requestLineStr.split(" ");
        String[] value1 = value[1].split("\\?");
        Protocol protocol = new Protocol(value[2]);
        QueryString queryString = new QueryString(value1[1]);
        return new RequestLine(value[0], value1[0], protocol, queryString);
    }
}
