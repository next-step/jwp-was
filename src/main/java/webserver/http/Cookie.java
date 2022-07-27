package webserver.http;

public class Cookie {

    private String name;

    private String value;

    private String path;

    public Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    String getValues() {
        return name + "=" + value + "; " + "Path" + "=" + path;
    }

}
