package http;

import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private final Map<String, String> HEADERS = new HashMap<>();

    public RequestHeader(final String header) {
        Tokens tokens = Tokens.init(header, "\n");
        tokens.getAllTokens()
                .forEach(this::addHeader);
    }

    private void addHeader(final String token) {
        Tokens tokens = Tokens.init(token, ":");

        HEADERS.put(tokens.nextToken().trim(), tokens.nextToken().trim());
    }

    public String getHeader(final String headerName) {
        return HEADERS.getOrDefault(headerName, null);
    }

    public String getCookie(final String cookieName) {
        return null;
    }
}
