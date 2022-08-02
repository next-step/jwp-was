package webserver;

public class Cookie {
    private final String key;
    private final String value;
    private final String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public Cookie(String key, String value) {
        this(key, value, null);
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
        if (path == null) {
            return String.format("%s=%s", key, value);
        }
        return String.format("%s=%s; Path=%s", key, value, path);
    }
}
