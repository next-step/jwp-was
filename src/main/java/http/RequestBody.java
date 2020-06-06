package http;

import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestBody {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    private final String origin;
    private final Map<String, String> PARAMS = new HashMap<>();

    public RequestBody(final String origin) {
        this.origin = origin;

        if (StringUtil.isEmpty(origin)) {
            return;
        }

        StringTokenizer tokens = new StringTokenizer(origin, PARAMETER_DELIMITER);

        while (tokens.hasMoreTokens()) {
            addHeader(tokens.nextToken());
        }
    }

    private void addHeader(final String token) {
        String[] tokens = token.split(KEY_VALUE_DELIMITER);

        if (tokens.length != 2) {
            throw new IllegalArgumentException("Header is invalid : " + token);
        }

        PARAMS.put(tokens[0], tokens[1]);
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
