package webserver;

import utils.StringUtils;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod resolve(String method) {
        if (StringUtils.isBlank(method)) {
            return null;
        }

        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findAny()
                .orElse(null);
    }
}
