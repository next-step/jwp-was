package http.request;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private final Map<String, String> value;

    public RequestBody(String body) {
        this.value = parse(body);
    }

    private Map<String, String> parse(String body) {
        if (body == null || body.isEmpty()) {
            return Map.of();
        }

        return Arrays.stream(body.split("&"))
            .map(it -> it.split("="))
            .map(it -> Map.entry(it[0], it[1]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, String> getParameters() {
        return new HashMap<>(value);
    }

    public String getValue(String key) {
        return value.get(key);
    }
}
