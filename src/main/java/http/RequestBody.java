package http;

import utils.StringUtil;
import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final String origin;
    private final Map<String, String> PARAMS = new HashMap<>();

    public RequestBody(final String origin) {
        this.origin = origin;

        if (StringUtil.isEmpty(origin)) {
            return;
        }

        Tokens tokens = Tokens.init(origin, "&");
        tokens.getAllTokens()
                .forEach(this::addHeader);
    }

    private void addHeader(final String token) {
        Tokens tokens = Tokens.init(token, "=");

        PARAMS.put(tokens.nextToken().trim(), tokens.nextToken().trim());
    }

    public String getOrigin() {
        return origin;
    }

    public String getBodyParameter(final String parameter) {
        return PARAMS.get(parameter);
    }

    public Map<String, String> getParameters() {
        return PARAMS;
    }
}
