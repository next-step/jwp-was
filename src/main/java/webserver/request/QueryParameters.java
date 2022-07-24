package webserver.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryParameters {

    private static final String EMPTY_VALUE = "";

    private final Map<String, String> parameters = new HashMap<>();

    public QueryParameters(final String parameters) {
        if (parameters == null || parameters.isBlank()) {
            return;
        }

        this.parameters.putAll(parse(parameters));
    }

    private Map<String, String> parse(final String parameters) {
        return Arrays.stream(parameters.split("&"))
            .map(token -> token.split("="))
            .filter(this::hasKey)
            .collect(Collectors.toMap(
                key -> key[0],
                this::getValue
            ));
    }

    private boolean hasKey(final String[] keyAndValue) {
        return keyAndValue.length > 0 && !keyAndValue[0].isBlank();
    }

    private String getValue(final String[] keyAndValue) {
        if (keyAndValue.length == 1) {
            return EMPTY_VALUE;
        }
        return keyAndValue[1];
    }

    public String get(final String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QueryParameters that = (QueryParameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParameters());
    }
}
