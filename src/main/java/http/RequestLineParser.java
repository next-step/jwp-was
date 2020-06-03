package http;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        final String[] splited = requestLine.split(" ");
        final QueryString queryString = new QueryString(splited[1].split("\\?")[1]);
        final Protocol protocol = new Protocol(splited[2].split("/"));
        return new RequestLine(splited[0], splited[1], protocol, queryString);
    }
}
