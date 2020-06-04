package http.enums;

public enum Method {
    GET("GET") , POST("POST");

    String name;

    Method(String method) {
        this.name = method;
    }
}
