package webserver.http.model.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private Map<String, String> responseHeaderMap;

    public ResponseHeaders() {
        responseHeaderMap = new HashMap<>();
    }

    public ResponseHeaders(Map<String, String> responseHeaderMap) {
        this.responseHeaderMap = responseHeaderMap;
    }

    public Map<String, String> getResponseHeaderMap() {
        return responseHeaderMap;
    }
}
