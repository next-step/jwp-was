package webserver.http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralHeader {

    private final Map<GeneralHeaderFields, CacheDirective> generalHeaders = new HashMap<>();

    public GeneralHeader(final List<String> httpRequestHeaders) {
    }
}
