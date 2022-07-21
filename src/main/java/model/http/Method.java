package model.http;

public enum Method {
    GET,
    POST;

    public static Method of(String methodName) {
        return valueOf(methodName);
    }
}
