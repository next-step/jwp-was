package http;

public class RequestHeader {

    private static final String SEPARATOR = ": ";

    private final String key;
    private final String value;

    public RequestHeader(final String readLine) {
        String[] tokens = readLine.split(SEPARATOR);
        this.key = tokens[0];
        this.value = tokens[1];
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
