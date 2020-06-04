package http;

import utils.Tokens;

public class HttpRequestParser {
    private static final String NEW_LINE = "\n";
    private HttpRequestParser() {}

    public static HttpRequest parse(final String rawRequestStr) {
        Tokens tokens = new Tokens(rawRequestStr, NEW_LINE);

        return HttpRequest.init(RequestLineParser.parse(tokens.nextToken()));
    }
}
