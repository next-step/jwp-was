package http;

public class RequestLineParser {

    // TODO: 뭔가 parse가 많은 일을 하고 있는거 같다는 생각이 드는데..?
    public static RequestLine parse(String requestLine) {
        final String[] tokens = requestLine.split(" ");
        final QueryString queryString = tokens[1].contains("?") ?
                new QueryString(tokens[1].split("\\?")[1]) : null;
        final String[] protocolAndVersion = tokens[2].split("/");
        final Protocol protocol = new Protocol(protocolAndVersion[0], protocolAndVersion[1]);
        return new RequestLine(tokens[0], tokens[1], protocol, queryString);
    }
}
