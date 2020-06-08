package http.request;

import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Parameters {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";
    private static final int KEY_AND_VALUE_TOKEN_SIZE = 2;

    private final Map<String, String> parameters;

    private Parameters() {
        this.parameters = new HashMap<>();
    }

    public static Parameters newInstance() {
        return new Parameters();
    }

    public static Parameters parse(final String query) {
        Parameters parameters = Parameters.newInstance();
        parameters.update(query);

        return parameters;
    }

    public void update(final String query) {
        if (StringUtil.isEmpty(query)) {
            return;
        }

        StringTokenizer tokens = new StringTokenizer(query, PARAMETER_DELIMITER);

        while (tokens.hasMoreTokens()) {
            parseQuery(tokens.nextToken());
        }
    }

    private void parseQuery(final String query) {
        String[] tokens = query.split(KEY_AND_VALUE_DELIMITER);

        if (tokens.length != KEY_AND_VALUE_TOKEN_SIZE) {
            throw new IllegalArgumentException("Invalid query string : " + query);
        }

        parameters.put(tokens[0], tokens[1]);
    }

    public String get(final String parameterName) {
        return parameters.get(parameterName);
    }
}
