package webserver.http.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> parameters = new HashMap<>();

    public RequestBody(final String rawRequestBody) {
        final String decodedRawRequestBody = URLDecoder.decode(rawRequestBody, StandardCharsets.UTF_8);
        final String[] keyAndValues = decodedRawRequestBody.split("&");

        for (final String keyAndValueString : keyAndValues) {
            final String[] keyAndValue = keyAndValueString.split("=");
            this.parameters.put(keyAndValue[0], keyAndValue[1]);
        }
    }

    public String get(final String key) {
        final String valueOrNull = this.getOrNull(key);

        if (valueOrNull == null) {
            throw new IllegalArgumentException("Key not found: " + key);
        }

        return valueOrNull;
    }

    public String getOrNull(final String key) {
        return this.parameters.get(key);
    }
}
