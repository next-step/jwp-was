package http.request;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String QUERY_PARAMETER_DELIMITER = "\\?";

    private HttpMethod httpMethod;
    private String path;
    private Protocol protocol;

    public RequestLine (String url) {
        String[] splits = url.split(REQUEST_LINE_DELIMITER);
        String[] pathIncludeParameters = splits[1].split(QUERY_PARAMETER_DELIMITER);
        validateHttpMethod(splits[0]);

        this.httpMethod = HttpMethod.valueOf(splits[0]);
        this.path = pathIncludeParameters[0];
        this.protocol = Protocol.of(splits[2]);
    }

    private void validateHttpMethod(String str) {
        if (!str.equals(str.toUpperCase())) {
            throw new IllegalArgumentException("Http 메소드는 대문자여야 합니다.");
        }
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getPath() {
        return path;
    }

    public String getProtocolType() {
        return protocol.getProtocolType();
    }

    public Double getVersion() {
        return protocol.getVersion();
    }
}
