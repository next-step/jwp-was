package webserver.request;

/**
 * Created by hspark on 2019-08-05.
 */
public class Cookie {
    private String name;
    private String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
