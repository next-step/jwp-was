package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultRequestParams implements  RequestParams {
    private static final Logger log = LoggerFactory.getLogger(DefaultRequestParams.class);

    private final String queryString;

    private final String body;

    public DefaultRequestParams(String queryString, String body) {
        this.queryString = queryString;
        this.body = body;
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new HashMap<>();
        putParams(params, queryString);
        putParams(params, body);
        return params;
    }

    @Override
    public String getParameter(String name) {
        return getParameters().get(name);
    }

    private static void putParams(Map<String, String> params, String data) {
        log.debug("data : {}", data);

        if (data != null && !data.isBlank()) {
            params.putAll(HttpRequestUtils.parseQueryString(data));
            log.debug("params : {}", params);
        }
    }
}
