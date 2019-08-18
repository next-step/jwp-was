package webserver.http.request;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestParams {
    private Map<String, String> httpRequestParams;

    private HttpRequestParams(Map<String, String> httpRequestParams) {
        this.httpRequestParams = httpRequestParams;
    }

    public static HttpRequestParams of(String parameterString) {
        patternCheck(parameterString);

        Map<String, String> httpRequestParams = Stream.of(parameterString.split(HttpStringType.DELIMITER_AMPERSAND.getType()))
                .map(params -> params.split(HttpStringType.DELIMITER_EQUAL_SIGN.getType()))
                .collect(Collectors.toMap(param -> param[0], param -> param[1]));

        return new HttpRequestParams(httpRequestParams);
    }

    private static boolean patternCheck(String params) {
        boolean isOk = HttpStringUtils.isPatternMatch(HttpStringType.QUERYSTRING_PATTERN.getType(), params);
        if (!isOk) {
            throw new IllegalArgumentException();
        }

        return true;
    }

    public Set<String> getKeys() {
        return httpRequestParams.keySet();
    }

    public String findByKey(String key) {
        return httpRequestParams.get(key);
    }
}
