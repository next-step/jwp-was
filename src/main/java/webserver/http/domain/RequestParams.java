package webserver.http.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestParams {

    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> params;

    public RequestParams() {
        params = new HashMap<>();
    }

    public RequestParams(String queryString) {
        this.params = create(queryString);
    }

    private Map<String, String> create(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .peek(this::validateQueryString)
                .collect(Collectors.toMap(params -> params[0], params -> params[1]));
    }

    private void validateQueryString(String[] requestParams) {
        if (requestParams.length != 2 || requestParams[0].isEmpty() || requestParams[1].isEmpty()) {
            throw new IllegalArgumentException("QueryString의 Parameter 값이 유효하지 않습니다. key=value 형태여야 합니다.");
        }
    }

    public Map<String, String> params() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParams that = (RequestParams) o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }
}
