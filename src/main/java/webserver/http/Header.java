package webserver.http;

class Header {

    public static Header EMPTY = new Header("", "");

    private final String name;

    private final String value;

    Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }
}
