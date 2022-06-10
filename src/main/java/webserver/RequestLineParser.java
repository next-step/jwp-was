package webserver;

public class RequestLineParser {
    private final String SPACE_DELIMITER = " ";
    private final String QUERY_STRING_DELIMITER = "\\?";
    private final String PROTOCOL_DELIMITER = "/";

    public RequestLine parse(String input) {
        String[] splitInput = input.split(SPACE_DELIMITER);

        String httpMethod = splitInput[0];

        String[] uri = splitInput[1].split(QUERY_STRING_DELIMITER);

        String path = uri[0];
        String queryString = null;

        if (uri.length > 1) {
            queryString = uri[1];
        }

        String[] protocol = splitInput[2].split(PROTOCOL_DELIMITER);

        return new RequestLine(httpMethod, path, protocol[0], protocol[1], queryString);
    }
}
