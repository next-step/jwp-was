package webserver.http;

public class Parameter {
    private String key;
    private String value;

    private Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Parameter newInstance(String key, String value) {
        return new Parameter(key, value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
