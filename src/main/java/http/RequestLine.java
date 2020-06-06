package http;

import java.util.Objects;

public class RequestLine {
    // 학습을 위해 spring에서 제공하고 있는 HTTPMethod 를 사용하지 않고, 만들어서 사용함
    private HttpMethod method;
    private String path;
    private QueryStrings queryStrings;
    private HTTPProtocol protocol;

    public RequestLine(String method, String path, String protocol) {
        this.method = HttpMethod.valueOf(method);

        String[] requestPathAndQueryString = path.split("\\?");
        this.path = requestPathAndQueryString[0];
        if(requestPathAndQueryString.length == 2) {
            this.queryStrings = new QueryStrings(requestPathAndQueryString[1]);
        }

        this.protocol = new HTTPProtocol(protocol);
    }

    public String getPath() {
        return path;
    }

    public QueryStrings getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(queryStrings, that.queryStrings) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, queryStrings, protocol);
    }
}
