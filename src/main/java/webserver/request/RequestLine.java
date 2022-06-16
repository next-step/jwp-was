package webserver.request;

import java.util.Objects;

public class RequestLine {
    private static final String BLANK = " ";
    
    private final HttpMethod httpMethod;
    private final Uri uri;
    private final Protocol protocol;

    private RequestLine(HttpMethod httpMethod, Uri uri, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine from(String readLine) {
        String[] lineSplit = readLine.split(BLANK);
        HttpMethod httpMethod = HttpMethod.from(lineSplit[0]);
        Uri uri = Uri.from(lineSplit[1]);
        Protocol protocol = Protocol.from(lineSplit[2]);
        return new RequestLine(httpMethod, uri, protocol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLine)) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uri, protocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Uri getUri() {
        return uri;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getPathStr() {
        return uri.getPathStr();
    }

    public boolean matchPath(String str) {
        return uri.matchPath(str);
    }

}
