package http.request;

import org.apache.logging.log4j.util.Strings;
import utils.UrlUtf8Decoder;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.WebServerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryString {
    private static final String PARAMETER_NAME_VALUE_TOKENIZER = "=";
    private static final String QUERYSTRING_TOKENIZER = "&";

    private Map<String, String> queries = new HashMap<>();

    public QueryString(String values) {
        if (Strings.isBlank(values)) {
            return;
        }

        for (String value : split(values)) {
            String[] token = value.split(PARAMETER_NAME_VALUE_TOKENIZER);
            if (token.length != 2) {
                throw new WebServerException(ErrorMessage.ILLEGAL_QUERY_STRING);
            }
            String parameterName = UrlUtf8Decoder.decode(token[0]);
            String parameterValue = UrlUtf8Decoder.decode(token[1]);
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
}
