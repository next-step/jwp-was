package http;

import org.springframework.http.HttpMethod;

import java.applet.Applet;

public class RequestLine {

    private final String method;
    private final String path;
    private final QueryStrings queryStrings;
    private final Protocol protocol;

    public RequestLine(String method, String path, String queryString, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.queryStrings = new QueryStrings(queryString);
        this.protocol = protocol;
    }

    public String getMapping() {
        return this.method;
    }

    public String getPath() { return this.path; }

    public Protocol getProtocol() { return this.protocol;}

    public QueryStrings getQueryString() { return this.queryStrings;}
}
