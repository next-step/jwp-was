package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class GeneralHeader implements RequestHeaderField {

    private final Map<GeneralHeaderFields, CacheDirective> generalHeaders = new HashMap<>();
}
