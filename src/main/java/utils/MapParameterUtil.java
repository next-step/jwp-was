package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class MapParameterUtil {
    public static final String PARAMETER_SPLITTER = "&";
    public static final String KEY_VALUE_SPLITTER = "=";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, String> buildParameters(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(collectParameterMap(queryString));
    }

    private static Map<String, String> collectParameterMap(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_SPLITTER))
                .filter(param -> param.contains(KEY_VALUE_SPLITTER))
                .map(param -> param.split(KEY_VALUE_SPLITTER, 2))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    public static <T> T toObject(Map map, Class<T> clazz) {
        return mapper.convertValue(map, clazz);
    }
}
