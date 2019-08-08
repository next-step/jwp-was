package webserver.http.request;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static boolean contains(String method) {
        return Stream.of(values()).anyMatch(httpMethod -> method.equals(httpMethod.name()));
    }

    public static boolean contains(HttpMethod method) {
        return Arrays.asList(values()).contains(method);
    }

    public boolean isGet() {
        return HttpMethod.GET == this;
    }

    public boolean isPost() {
        return HttpMethod.POST == this;
    }

    public boolean isPut() {
        return HttpMethod.PUT == this;
    }

    public boolean isDelete() {
        return HttpMethod.DELETE == this;
    }
}
