package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestUriFactory {

    private static final String PATH_SEPARATOR = "\\?";
    private static final String QUERY_PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private String path;
    private Map<String, String> queryParams;

    private RequestUriFactory(String path, String queryString) {
        this.path = path;
        this.queryParams = new HashMap<>();

        addQueryParams(queryString.split(QUERY_PARAM_SEPARATOR));
    }

    private RequestUriFactory(String path) {
        this.path = path;
        this.queryParams = Collections.emptyMap();
    }

    public static RequestUriFactory parse(String line) {
        String[] split = line.split(PATH_SEPARATOR);

        if (!hasQueryParams(split)) {
            return new RequestUriFactory(split[0]);
        }

        return new RequestUriFactory(split[0], split[1]);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getPath() {
        return path;
    }

    private static String[] getKeyValue(String queryString) {
        return queryString.split(KEY_VALUE_SEPARATOR);
    }

    private static boolean hasQueryParams(String[] split) {
        return split.length > 1;
    }

    private void addQueryParams(String[] queryParams) {

        if (isEmpty(queryParams)) {
            return;
        }

        Arrays.stream(queryParams)
                .forEach(this::addQueryParam);
    }

    private void addQueryParam(String queryParam) {
        String[] keyValue = getKeyValue(queryParam);

        if (!isPair(keyValue)) {
            return;
        }

        this.queryParams.put(keyValue[0], keyValue[1]);
    }

    private boolean isEmpty(String[] target) {
        return target.length == 0;
    }

    private static boolean isPair(String[] keyValue) {
        return keyValue.length == 2;
    }
}
