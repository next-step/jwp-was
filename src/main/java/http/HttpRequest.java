package http;

public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers = new Headers();
    private QueryStrings queryStrings;

    public void addRequestLine(String requestLineString) {
        this.requestLine = RequestLineParser.parse(requestLineString);
    }

    public void addHeader(String requestHeaderLine) {
        this.headers.add(requestHeaderLine);
    }

    public void addRequestBody(String requestBody) {
        this.queryStrings = new QueryStrings(requestBody);
    }

    public boolean matchPath(String requestPath) {
        return requestLine.matchPath(requestPath);
    }

    public boolean pathEndsWith(String extension) {
        return requestLine.pathEndsWith(extension);
    }

    public boolean isGetRequest() {
        return requestLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return requestLine.isPostRequest();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getHeaderValue(HeaderName headerType) {
        return headers.getHeader(headerType).getValue();
    }

    public QueryStrings getQueryString() {
        return this.queryStrings;
    }

}
