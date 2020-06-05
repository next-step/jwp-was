package http;

import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final String origin;
    private final Map<String, String> PARAMS = new HashMap<>();

    public RequestBody(final String origin) {
        this.origin = origin;

        Tokens tokens = Tokens.init(origin, "\n");
        tokens.getAllTokens()
                .forEach(this::addHeader);
    }

    private void addHeader(final String token) {
        Tokens tokens = Tokens.init(token, ":");

        PARAMS.put(tokens.nextToken().trim(), tokens.nextToken().trim());
    }

    public String getOrigin() {
        return origin;
    }

    public String getBodyParameter(String parameter) {
        return PARAMS.get(parameter);
    }
}
