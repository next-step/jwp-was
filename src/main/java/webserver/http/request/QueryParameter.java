package webserver.http.request;

import utils.DecoderUtils;
import webserver.http.Url;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class QueryParameter {
    private static final String QUERY_PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int NO_QUERY_PARAMETER = 1;

    private final Map<String, String> parameters;

    public QueryParameter(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryParameter parseFrom(final String[] splitUrl) {
        if (splitUrl.length == NO_QUERY_PARAMETER) {
            return new QueryParameter(Collections.EMPTY_MAP);
        }

        String[] splitParameters = splitUrl[Url.QUERY_PARAMETER_INDEX].split(QUERY_PARAMETER_DELIMITER);

        return new QueryParameter(
                Arrays.stream(splitParameters)
                        .map(value -> value.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> DecoderUtils.decode(entry[0]), entry -> DecoderUtils.decode(entry[1])))
        );
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        if (!parameters.containsKey(key)) {
            throw new NoSuchElementException();
        }
        return parameters.get(key);
    }
}
