package webserver.http.enums;

import java.util.Arrays;

public enum HTTPMethod {

    GET, POST, PUT, DELETE;

    public static HTTPMethod httpMethod(String httpMethod) {
        return Arrays.stream(HTTPMethod.values())
                .filter(httpMethod1 -> httpMethod1.name().equals(httpMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 HTTP Method 입니다."));
    }
}
