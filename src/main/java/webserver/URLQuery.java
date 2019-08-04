package webserver;

import utils.MapUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    /**
     * @return if parameter is empty or decoding fail, return null
     */
    public String getParameter(String key) {
        if (! queryMap.containsKey(key)) {
            return null;
        }

        try {
            return URLDecoder.decode(queryMap.get(key), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
