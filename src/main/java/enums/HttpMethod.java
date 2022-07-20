package enums;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, UNKNOWN;

    public static HttpMethod getHttpMethod(String httpMethod) throws IllegalArgumentException {
        Optional<HttpMethod> matchedHttpMethod = Arrays.stream(values()).filter(it -> it.name().equals(httpMethod)).findFirst();

        return matchedHttpMethod.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 형식의 HttpMethod 입니다."));
    }
}