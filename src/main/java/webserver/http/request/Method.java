package webserver.http.request;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Method {
    GET, POST;

    private static final Map<String, Method> METHODS;

    static {
        METHODS = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableMap(Method::name, Function.identity()));
    }

    public static Method from(String value) {
        String method = value.toUpperCase();
        if (METHODS.containsKey(method)) {
            return METHODS.get(method);
        }
        throw new RuntimeException(String.format("현재 서버에서 처리할 수 없는 메소드입니다. {: %s}", value));
    }
}
