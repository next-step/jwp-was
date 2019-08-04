package webserver.http;

public class Cookie {
    private final String name;
    private String value;
    private String path;

    public Cookie(String name, String value) {
        this(name, value, "/");
    }

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("Set-Cookie: %s=%s; Path=%s", name, value, path).trim();
    }
}
