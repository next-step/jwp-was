package webserver.http.request;

public class RequestLine {
    private final Method method;
    private final URI uri;
    private final Protocol protocol;

    public RequestLine(Method method, URI uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public boolean isGetMethod() {
        return method.isGet();
    }

    public String getPath() {
        return uri.getPath();
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", uri=" + uri +
                ", protocol=" + protocol +
                '}';
    }
}
