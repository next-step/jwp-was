package webserver.response;

public class Cookie {
    private final String name;
    private final String value;
    private String path = "/";

    public Cookie(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getPath() {
        return this.path;
    }
}
