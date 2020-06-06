package http;

import java.util.Arrays;

public class RawRequest {

    private static final String NEW_LINE = "\n";

    private final String requestLine;
    private final String headers;
    private final String body;

    public RawRequest(final String rawRequestStr) {
        String[] tokens = rawRequestStr.split(NEW_LINE);

        requestLine = tokens[0];
        headers = extractHeaders(Arrays.copyOfRange(tokens, 1, tokens.length));
        body = extractBody(tokens);
    }

    private String extractBody(final String[] tokens) {
        int index = 0;

        while (index < tokens.length && !tokens[index].isEmpty()) {
            index++;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index + 1 ; i < tokens.length ; ++i) {
            stringBuilder.append(tokens[i])
                    .append(NEW_LINE);
        }

        return stringBuilder.toString();
    }

    private String extractHeaders(final String[] tokens) {
        StringBuilder stringBuilder = new StringBuilder();

        int index = 0;

        while (index < tokens.length && !tokens[index].isEmpty()) {
            stringBuilder.append(tokens[index])
                    .append(NEW_LINE);
            index++;
        }

        return stringBuilder.toString();
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
