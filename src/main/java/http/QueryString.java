package http;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import utils.StringUtils;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QueryString {
    private static final String AMPERSAND_DELIMITER = "&";
    private static final String EQUALS_SIGN = "=";

    private MultiValueMap<String, String> parameters;

    private QueryString(MultiValueMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public static QueryString from(String fullQueryString) {
        String[] pairs = fullQueryString.split(AMPERSAND_DELIMITER);

        MultiValueMap<String, String> parameters = Arrays.stream(pairs)
                .filter(pair -> !pair.isEmpty())
                .map(pair -> StringUtils.splitIntoPair(pair, EQUALS_SIGN))
                .collect(LinkedMultiValueMap::new,
                        (m, v) -> m.add(v[0], StringUtils.convertToNullIfEmpty(v[1])),
                        LinkedMultiValueMap::addAll);
        return new QueryString(parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    public List<String> getParameters(String key) {
        return parameters.get(key);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }
}
