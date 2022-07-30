package webserver.http.view.request;

import webserver.http.domain.KeyValuePair;
import webserver.http.domain.request.Parameters;
import webserver.http.view.KeyValuePairParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParametersParser {

    private static final String KEY_VALUE_DELIMITER_REGEX = "&";

    private static final String KEY_VALUE_PAIR_DELIMITER = "=";

    private final KeyValuePairParser keyValuePairParser;

    public ParametersParser(KeyValuePairParser keyValuePairParser) {
        this.keyValuePairParser = keyValuePairParser;
    }

    public Parameters parse(String message) {
        if (message.isBlank()) {
            return new Parameters(new HashMap<>());
        }

        Map<String, List<String>> keyValues = Arrays.stream(message.split(KEY_VALUE_DELIMITER_REGEX))
                .map(keyValue -> keyValuePairParser.parse(keyValue, KEY_VALUE_PAIR_DELIMITER, false))
                .collect(Collectors.toMap(KeyValuePair::getKey,
                        pair -> {
                            List<String> subList = new ArrayList<>();
                            subList.add(pair.getValue());
                            return subList;
                        },
                        (left, right) -> {
                            left.addAll(right);
                            return left;
                        }
                ));

        return new Parameters(keyValues);
    }
}
