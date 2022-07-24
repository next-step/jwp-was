package webserver.http;

import utils.HttpRequestUtils;

import java.util.Map;

public class RequestBody {

    private Map<String, String> requestData;

    public RequestBody(String queryString) {
        this.requestData = HttpRequestUtils.queryStringParser(queryString);
    }

    public void parse(String requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            return;
        }
        this.requestData.putAll(HttpRequestUtils.queryStringParser(requestBody));
    }

    public String getParameter(String key) {
        return requestData.get(key);
    }
}
