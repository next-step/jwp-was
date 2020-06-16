package http.request;

import com.google.common.collect.Maps;
import utils.StringParseUtils;

import java.util.Map;

public class RequestParams {
    private final Map<String, String> values = Maps.newHashMap();

    public RequestParams() {
    }

    public void addParams(final String queryString) {
        values.putAll(StringParseUtils.parseRequestParam(queryString));
    }

    public String getParameter(final String key) {
        return values.getOrDefault(key, null);
    }

    public void addAttribute(final String key, final String value) {
        values.put(key, value);
    }

}
