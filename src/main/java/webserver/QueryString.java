package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_IDX = 0;
    private static final int VALUE_IDX = 1;

    private Map<String, String> queryData;

    public QueryString(Map<String, String> queryData) {
        this.queryData = queryData;
    }

    public static QueryString parse(String queryString) {
        String[] datas = queryString.split(PARAM_DELIMITER);
        Map<String, String> data = Arrays.stream(datas)
                .map(s -> (s.split(KEY_VALUE_DELIMITER)))
                .collect(Collectors.toMap(s -> s[KEY_IDX], s -> s[VALUE_IDX]));

        return new QueryString(data);
    }

    public static QueryString parse() {
        return new QueryString(Collections.EMPTY_MAP);
    }

    public Map<String, String> getQueryData() {
        return queryData;
    }

    public String getValue(String key) {
        return queryData.get(key);
    }
}
