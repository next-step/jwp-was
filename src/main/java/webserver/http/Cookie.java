package webserver.http;

public class Cookie {
    private String key;
    private String value;
    private String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public Cookie(String key, String value) {
        this(key, value, "/");
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
        return new StringBuilder()
                .append(key).append("=").append(value).append(";")
                .append("Path=").append(path).toString();
    }
}
