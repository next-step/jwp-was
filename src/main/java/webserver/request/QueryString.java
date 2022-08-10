package webserver.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.StringUtils;

public class QueryString {
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> params;

    public QueryString(String queryString) {
        this.params = Stream.of(queryString.split(PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(value -> value[0], value -> StringUtils.decodeUrlEncoding(value[1])));
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public String getParam(String key) {
        return params.get(key);
    }
}
