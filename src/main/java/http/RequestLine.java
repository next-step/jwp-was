package http;

import org.springframework.http.HttpMethod;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public String getMapping() {
        return this.method;
    }

    public String getPath() { return this.path; }

    public String getProtocol() { return this.protocol; }

    public String getVersion() { return this.version;}
}
