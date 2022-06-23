package webserver.http.request;

public class Cookie {
    private String name;

    private String value;

    private String path;

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value, null);
    }

    public static Cookie of(String name, String value, String path) {
        return new Cookie(name, value, path);
    }
}
