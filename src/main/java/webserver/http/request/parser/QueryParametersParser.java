package webserver.http.request.parser;

import webserver.http.request.KeyValuePair;
import webserver.http.request.QueryParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParametersParser {

    private static final String KEY_VALUE_DELIMITER = "&";

    private static final String KEY_VALUE_PAIR_DELIMITER = "=";

    private final KeyValuePairParser keyValuePairParser;

    public QueryParametersParser(KeyValuePairParser keyValuePairParser) {
        this.keyValuePairParser = keyValuePairParser;
    }

    public QueryParameters parse(String message) {
        if (message.isBlank()) {
            return new QueryParameters(new HashMap<>());
        }

        Map<String, List<String>> keyValues = Arrays.stream(message.split(KEY_VALUE_DELIMITER))
                .map(keyValue -> keyValuePairParser.parse(keyValue, KEY_VALUE_PAIR_DELIMITER))
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

        return new QueryParameters(keyValues);
    }
}
