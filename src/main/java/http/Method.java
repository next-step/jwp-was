package http;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public enum Method {
    GET("GET"), POST("POST");

    private final String method;

    Method(String method) {
        this.method = method;
    }

}
