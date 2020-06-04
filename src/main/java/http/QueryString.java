package http;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryString {
    private Map<String, String> queries = new HashMap<>();

    private static final String PARAMETER_NAME_VALUE_TOKENIZER = "=";
    private static final String QUERYSTRING_TOKENIZER = "&";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return queries.equals(that.queries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queries);
    }

    public QueryString(String values) {
        if (Strings.isBlank(values)) {
            return;
        }

        for(String value : split(values)) {
            String[] token = value.split(PARAMETER_NAME_VALUE_TOKENIZER);
            if (token.length != 2) {
                throw new RuntimeException("queryString 자르기 실패");
            }
            String parameterName = token[0];
            String parameterValue = token[1];
            queries.put(parameterName, parameterValue);
        }
    }

    public String getParameterValue(String parameterName) {
        String defaultValue = "";
        return queries.getOrDefault(parameterName, defaultValue);
    }

    private String[] split(String values) {
        return values.split(QUERYSTRING_TOKENIZER);
    }

}
