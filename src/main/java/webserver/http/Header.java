package webserver.http;

public class Header {

    public static Header EMPTY = new Header("", "");

    private final String name;

    private final String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }

    boolean equalsName(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }
}
