package webserver;

public class RequestLineParser {
    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public RequestLineParser(String requestLine) {
        String[] requestLineElements = requestLine.split(" ");
        this.method = requestLineElements[0];
        this.path = requestLineElements[1];
        this.protocol = requestLineElements[2].split("/")[0];
        this.version = requestLineElements[2].split("/")[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
