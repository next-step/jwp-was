package webserver;

public class RequestLine {
    private static final int MIN_VALUE_SIZE = 3;
    private static final String DELIMITER = " ";
    private String[] parsed;

    private Protocol protocol;

    public RequestLine(final String value) {
        validEmpty(value);

        String[] values = value.split(DELIMITER);

        if (values.length < MIN_VALUE_SIZE) {
            throw new IllegalArgumentException();
        }

        this.parsed = values;
        this.protocol = Protocol.from(values[2]);
    }

    private void validEmpty(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
    }

    public HttpMethod getMethod() {
        return HttpMethod.valueOf(parsed[0]);
    }

    public String getPath() {
        return parsed[1];
    }

    public String getProtocol() {
        return protocol.getName();
    }

    public String getProtocolVersion() {
        return protocol.getVersion();
    }

    public QueryString toQueryString() {
        return QueryString.parse(parsed[1]);
    }
}
