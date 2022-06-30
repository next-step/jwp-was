package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    private static final Logger log = LoggerFactory.getLogger(RequestParams.class);

    private final Map<String, String> params = new HashMap<>();

    public void addQueryString(String queryString) {
        putParams(queryString);
    }

    private void putParams(String data) {
        log.debug("data : {}", data);

        if (data != null && !data.isBlank()) {
            params.putAll(HttpRequestUtils.parseQueryString(data));
            log.debug("params : {}", params);
        }
    }

    public void addBody(String body) {
        putParams(body);
    }

    public String getParameter(String name) {
        return params.get(name);
    }
}
