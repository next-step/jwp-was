package http;

public class RequestLine {
    private static final int MIN_VALUE_SIZE = 3;
    private static final String DELIMITER = " ";

    private RequestMethod requestMethod;
    private QueryString queryString;
    private Protocol protocol;

    public RequestLine(final String value) {
        validEmpty(value);

        String[] values = value.split(DELIMITER);

        if (values.length < MIN_VALUE_SIZE) {
            throw new IllegalArgumentException();
        }

        this.requestMethod = RequestMethod.valueOf(values[0]);
        this.queryString = QueryString.parse(values[1]);
        this.protocol = Protocol.from(values[2]);
    }

    private void validEmpty(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
    }

    public RequestMethod getMethod() {
        return requestMethod;
    }

    public String getPath() {
        return queryString.getPath();
    }

    public String getProtocol() {
        return protocol.getName();
    }

    public String getProtocolVersion() {
        return protocol.getVersion();
    }

    public QueryString toQueryString() {
        return queryString;
    }
}
