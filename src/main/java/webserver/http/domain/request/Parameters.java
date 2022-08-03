package webserver.http.domain.request;

import com.google.common.base.Charsets;
import webserver.http.domain.KeyValuePair;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parameters {
    private static final String KEY_VALUE_DELIMITER_REGEX = "&";

    private static final String KEY_VALUE_PAIR_DELIMITER = "=";

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

    public static Parameters from(String message) {
        if (message.isBlank()) {
            return new Parameters(new HashMap<>());
        }

        Map<String, List<String>> keyValues = Arrays.stream(message.split(KEY_VALUE_DELIMITER_REGEX))
                .map(keyValue -> KeyValuePair.from(keyValue, KEY_VALUE_PAIR_DELIMITER, false))
                .collect(Collectors.toMap(KeyValuePair::getKey,
                        pair -> {
                            List<String> subList = new ArrayList<>();
                            subList.add(getValueAndDecode(pair));
                            return subList;
                        },
                        (left, right) -> {
                            left.addAll(right);
                            return left;
                        }
                ));

        return new Parameters(keyValues);
    }

    private static String getValueAndDecode(KeyValuePair pair) {
        return URLDecoder.decode(pair.getValue(), Charsets.UTF_8);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "keyValues=" + keyValues +
                '}';
    }
}
