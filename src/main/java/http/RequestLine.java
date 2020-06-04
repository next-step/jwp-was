package http;

public class RequestLine {
    private Method method;
    private Path path;
    private ProtocolAndVersion protocolAndVersion;

    public RequestLine(String requestLine) {
        String[] strRequestLine = requestLine.split(" ");
        this.method = Method.valueOf(strRequestLine[0]);
        this.path = new Path(strRequestLine[1]);
        this.protocolAndVersion = new ProtocolAndVersion(strRequestLine[2]);
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