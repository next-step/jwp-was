package webserver.http.request;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Parameters {
    private final Map<String, List<String>> keyValues;

    public Parameters(Map<String, List<String>> keyValues) {
        this.keyValues = keyValues;
    }

    public void add(Parameters parameters) {
        keyValues.putAll(parameters.keyValues);
    }

    public String get(String key) {
        List<String> strings = keyValues.get(key);
        if (Objects.isNull(strings) || strings.isEmpty()) {
            return null;
        }
        return strings.get(0);
    }

    public void decodeCharacter(Charset charset) {
        keyValues.forEach(
                (key, values) ->
                    values.replaceAll(value -> URLDecoder.decode(value, charset)
                )
        );
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "keyValues=" + keyValues +
                '}';
    }
}
