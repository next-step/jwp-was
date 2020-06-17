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

    public QueryString(String fullQueryString) {
        String[] pairs = fullQueryString.split(AMPERSAND_DELIMITER);

        this.parameters = Arrays.stream(pairs)
                .filter(pair -> !StringUtils.isEmpty(pair) && !EQUALS_SIGN.equals(pair))
                .map(pair -> StringUtils.splitIntoPair(pair, EQUALS_SIGN))
                .collect(LinkedMultiValueMap::new,
                        (m, v) -> m.add(
                                StringUtils.decode(v[0]),
                                StringUtils.decode(StringUtils.convertToNullIfEmpty(v[1]))),
                        LinkedMultiValueMap::addAll);
    }

    public List<String> getParameter(String field) {
        return parameters.get(field);
    }

    public String getFirstParameter(String field) { return parameters.getFirst(field); }

    public int size() {
        return parameters.size();
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
}
