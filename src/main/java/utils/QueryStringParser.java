package utils;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {

    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PARAMS_DELIMITER = "&";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private QueryStringParser() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    public static Map<String, String> parse(String queryString) {
        if(StringUtils.isEmpty(queryString)) {
            return Collections.emptyMap();
        }

        String decodedQueryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8);

        return Arrays.stream(decodedQueryString.split(PARAMS_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .map(QueryStringParser::parseToEntry)
                .collect(Collectors.toUnmodifiableMap((Map.Entry::getKey), (Map.Entry::getValue)));
    }

    private static Map.Entry<String, String> parseToEntry(String[] keyValuePair) {
        if (keyValuePair.length == 1) {
            return Map.entry(keyValuePair[KEY_INDEX], "");
        }

        return Map.entry(keyValuePair[KEY_INDEX], keyValuePair[VALUE_INDEX]);
    }
}
