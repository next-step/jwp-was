package webserver.http.request;

import java.util.Arrays;

public enum Method {
    GET, POST;

    public static Method from(String value) {
        String method = value.toUpperCase();
        return Arrays.stream(Method.values())
                .filter(methodValue -> method.equals(methodValue.name()))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("현재 서버에서 처리할 수 없는 메소드입니다. {: %s}", value)));
    }

    public boolean isGet() {
        return GET == this;
    }
}
