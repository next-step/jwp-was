package webserver.http.model;

public enum Method {
    GET,
    POST;

    public static Method of(String methodName) {
        return valueOf(methodName);
    }
}
