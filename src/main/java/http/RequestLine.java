package http;

public class RequestLine {
    private Method method;
    private Path path;
    private ProtocolAndVersion protocolAndVersion;

    public RequestLine(String requestLine) {
        String[] str = requestLine.split(" ");
        this.method = Method.valueOf(str[0]);
        this.path = new Path(str[1]);
        String[] protocolAndVersion = str[2].split("/");
        this.protocolAndVersion = new ProtocolAndVersion(str[2]);
    }

    public static RequestLine parse(String s) {
        return new RequestLine(s);
    }

    public Method getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public ProtocolAndVersion getProtocolAndVersion() {
        return protocolAndVersion;
    }
}