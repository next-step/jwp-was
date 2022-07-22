package http.httprequest.requestline;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Params {

    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String PARAMS_DELIMITER = "&";
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    private final Map<String, String> params;

    public Params(Map<String, String> params) {
        this.params = params;
    }

    public static Params from(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return new Params(Collections.emptyMap());
        }

        Map<String, String> params = parseQueryString(queryString);

        return new Params(params);
    }

    private static Map<String, String> parseQueryString(String queryString) {
         return Arrays.stream(queryString.split(PARAMS_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap((entry -> entry[KEY_INDEX]), (entry -> entry[VALUE_INDEX])));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Params params1 = (Params) o;
        return params.equals(params1.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public String toString() {
        return "Params{" +
                "params=" + params +
                '}';
    }
}
