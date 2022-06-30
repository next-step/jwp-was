package webserver.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CachedRequestParams implements RequestParams {
    private RequestParams requestParams;

    private final List<Map<String, String>> cached = new ArrayList<>(1);

    public CachedRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    @Override
    public Map<String, String> getParameters() {
        if (cached.isEmpty()) {
            cached.add(requestParams.getParameters());
        }
        return cached.get(0);
    }

    @Override
    public String getParameter(String name) {
        return getParameters().get(name);
    }
}
