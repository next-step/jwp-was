package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Http 의 queryString 이나 request Body의 정보를 담는 일급 컬렉션
 */
public class Parameters {
    public static final String QUERYSTRING_PARAM_DELIMITER = "&";
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String KEY_VALUE_DELIMITER_QUERYSTRING = "=";
    public static final String KEY_VALUE_DELIMITER = ": ";
    public static final int QUERY_VALUE_POINT = 1;
    public static final String DEFAULT_QUERY_VALUE = "";

    private final Map<String, String> store;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Parameters(@JsonProperty("parameters") Map<String, String> store) {
        this.store = store;
    }

    /**
     * 키/값 문자열을 Map 자료구조로 변환하여 일급 컬렉션을 생성해 반환한다.
     *
     * @param parameters 키/값 목록 문자열
     */
    public static Parameters from(String parameters) {
        return new Parameters(getParameterMap(parameters));
    }

    private static Map<String, String> getParameterMap(String parameters) {
        String trimParams = parameters.trim();
        String[] params = trimParams.split(LINE_SEPARATOR);

        if (params.length > 1) {
            return getParameterMap(params, KEY_VALUE_DELIMITER);
        }

        params = trimParams.split(QUERYSTRING_PARAM_DELIMITER);

        if (params.length > 1) {
            return getParameterMap(params, KEY_VALUE_DELIMITER_QUERYSTRING);
        }

        return new HashMap<>();
    }

    private static Map<String, String> getParameterMap(String[] parameters, String delimiter) {
        return Arrays.stream(parameters)
                .map(str -> str.split(delimiter))
                .collect(Collectors.toMap(Parameters::queryKey, Parameters::queryValue));
    }


    private static String queryKey(String[] pair) {
        return pair[0];
    }

    private static String queryValue(String[] pair) {
        if (pair.length < 2) {
            return DEFAULT_QUERY_VALUE;
        }
        return pair[QUERY_VALUE_POINT];
    }

    /**
     * 변경불가능한 빈 속성들을 담은 일급 컬렉션을 생성해 반환한다.
     */
    public static Parameters emptyInstance() {
        return new Parameters(Collections.emptyMap());
    }

    /**
     * 내부 값을 변경 가능한 일급 컬렉션을 생성해 반환한다.
     */
    public static Parameters newInstance() {
        return new Parameters(new HashMap<>());
    }

    /**
     * 키/값 자료구조를 불변 객체로 만들어 반환한다.
     */
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(store);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parameters that = (Parameters) o;
        return Objects.equals(store, that.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store);
    }

    /**
     * 속성 정보를 찾아 반환한다.
     *
     * @param key 속성 이름
     * @return 속성 정보
     */
    public String get(String key) {
        return store.get(key);
    }

    /**
     * 키/값 목록 문자열을 가공해 속성을 추가한다.
     *
     * @param line 키/값 목록 문자열
     */
    public void addParameters(String line) {
        store.putAll(getParameterMap(line));
    }

    /**
     * 속성 정보를 추가한다.
     *
     * @param key 속성 이름
     * @param value 속성
     */
    public void addParameters(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String toString() {
        return store.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + "\r\n")
                .collect(Collectors.joining());
    }

    /**
     * 속성 정보가 비어있는지 논리값을 반환한다.
     */
    public boolean isEmpty() {
        return store.isEmpty();
    }
}
