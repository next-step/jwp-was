package model;

import java.util.Arrays;

public enum HttpMethodType {
    GET, POST;

    public static HttpMethodType getType(String method) {
        return Arrays.stream(HttpMethodType.values())
                .filter(type -> type.equals(HttpMethodType.valueOf(method)))
                .findFirst()
                .orElseThrow(UnSupportMethodType::new);
    }
}
