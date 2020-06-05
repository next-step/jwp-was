package http;

import utils.Tokens;

import java.util.List;

public class HttpRequestParser {
    private static final String NEW_LINE = "\n";
    private HttpRequestParser() {}

    public static HttpRequest parse(final String rawRequestStr) {
        Tokens tokens = new Tokens(rawRequestStr, NEW_LINE);

        String requestLine = tokens.nextToken();
        List<String> requestHeaders = extractRequestHeaders(tokens);

        return HttpRequest.init(RequestLineParser.parse(requestLine));
    }

    private static List<String> extractRequestHeaders(final Tokens tokens) {
        String line;
        tokens.getAllTokens().stream().forEach(System.out::println);
        return null;
    }
}
