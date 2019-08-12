package webserver.http.request;

import webserver.http.util.RequestUtils;

import java.util.Map;

public class QueryParameter {

    private Map<String, String> queryParameters;

    public QueryParameter(String value) {
        this.queryParameters = RequestUtils.parseQueryParameter(value);
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }

    @Override
    public String toString() {
        return "QueryParameter{" +
                "queryParameters=" + queryParameters +
                '}';
    }
}
