package http;

import java.util.Map;

public class QueryString {
    private Map<String, String> queries;

    private static final String KEY_VALUE_TOKENIZER = "=";
    private static final String QUERYSTRING_TOKENIZER = "&";

    public QueryString(String values) {

        for(String value : split(values)) {
            String[] token = value.split(KEY_VALUE_TOKENIZER);
            if (token.length != 2) {
                throw new RuntimeException("queryString 자르기 실패");
            }
            String key = token[0];
            String val = token[1];
            queries.put(key, val);
        }

    }

    private String[] split(String values) {
        return values.split(QUERYSTRING_TOKENIZER);
    }

}
