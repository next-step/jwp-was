package webserver.http;

public class Cookie {

    public static final Cookie empty = new Cookie(null, null, null);

    private String key;

    private String value;

    private String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static Cookie of(String key, String value, String path) {
        return new Cookie(key, value, path);
    }

    public static Cookie of(String key, String value) {
        return new Cookie(key, value, "");
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.format("%s=%s; Path=%s", key, value, path);
    }
}
