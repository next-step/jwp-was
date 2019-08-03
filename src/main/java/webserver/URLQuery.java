package webserver;

import utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static utils.StringUtils.endSplit;
import static utils.StringUtils.frontSplitWithOrigin;

public class URLQuery {

    private static final String QUERY_FIELD_DELIMITER = "&";
    private static final char KEY_VALUE_DELIMITER = '=';

    private Map<String, String> queryMap;

    public URLQuery(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    /**
     * @param query is key=value string split '&'
     * @return key/value query map
     */
    public static URLQuery createUrlQuery(String query) {
        String[] parameters = query.split(QUERY_FIELD_DELIMITER);

        if (parameters.length == 0) {
            return new URLQuery(emptyMap());
        }

        Map<String, String> queryMap = new HashMap<>(parameters.length);
        for (String parameter : parameters) {
            String key = frontSplitWithOrigin(parameter, KEY_VALUE_DELIMITER);
            MapUtils.putIfKeyNotBlank(queryMap, key, endSplit(parameter, KEY_VALUE_DELIMITER));
        }
        return new URLQuery(queryMap);
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }
}
