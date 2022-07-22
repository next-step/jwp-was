package webserver.http.request;

import java.util.List;
import java.util.Map;

public class QueryParameters {
    private final Map<String, List<String>> keyValues;

    public QueryParameters(Map<String, List<String>> keyValues) {
        this.keyValues = keyValues;
    }
}
