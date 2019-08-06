package webserver.http;

import utils.MapUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {
    private final static String PATH_SEPARATOR = "?";
    private final static String PARAMETER_SEPARATOR = "&|;";
    private final static String KEY_VALUE_SEPARATOR = "=";
    private static final String NO_SUCH_ELEMENT_EXCEPTION_MESSAGE = "Key에 맞는 Value가 없습니다.";

    private Map<String, LinkedList<String>> parameterMap;

    private QueryString(Map<String, LinkedList<String>> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public static QueryString parse(String url) {
        Map<String, LinkedList<String>> parameterMap = MapUtils.keyMultiValueMap(getParameterStrings(url).stream(), KEY_VALUE_SEPARATOR);
        return new QueryString(parameterMap);
    }

    private static List<String> getParameterStrings(String url) {
        String queryString = url.substring(url.indexOf(PATH_SEPARATOR) + 1);
        return Stream.of(queryString.split(PARAMETER_SEPARATOR))
                .filter(value -> value.contains(KEY_VALUE_SEPARATOR))
                .collect(Collectors.toList());
    }

    public int parameterSize() {
        return parameterMap.size();
    }

    public List<String> get(String key) {
        return parameterMap.get(key);
    }

    public String getFirst(String key) {
        return Optional.ofNullable(parameterMap.get(key))
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ELEMENT_EXCEPTION_MESSAGE))
                .getFirst();
    }
}
