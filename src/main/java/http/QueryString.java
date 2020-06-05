package http;

import org.apache.logging.log4j.util.Strings;
import utils.UrlUtf8Decoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
                throw new RuntimeException("queryString 자르기 실패");
            }
            String parameterName = decode(token[0]);
            String parameterValue = decode(token[1]);
            queries.put(parameterName, parameterValue);
        }
    }

    private String decode(String encodedValue) {
        final String EncodeType = "UTF-8";
        String result = null;
        try {
            result = URLDecoder.decode(encodedValue, EncodeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("지원하지 않는 Encoding 타입. Type : [" + EncodeType + "]");
        }
        return result;
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
