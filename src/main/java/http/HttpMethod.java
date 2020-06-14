package http;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public enum HttpMethod {
    GET("GET"), POST("POST");

    protected static final String ILLEGAL_HTTP_METHOD = "%s 은 유효하지 않은 Method 입니다.";
    private static final List<String> HTTP_METHODS = Arrays.stream(values())
        .map(Enum::toString)
        .collect(Collectors.toList());
    private final String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static HttpMethod of(String methodInput) {
        if (!isHttpMethod(methodInput)) {
            throw new IllegalArgumentException(String.format(ILLEGAL_HTTP_METHOD, methodInput));
        }
        return HttpMethod.valueOf(methodInput);
    }

    private static boolean isHttpMethod(String methodInput) {
        return HTTP_METHODS.contains(methodInput);
    }
}
