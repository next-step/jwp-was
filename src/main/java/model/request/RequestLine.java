package model.request;

public class RequestLine {

    private Method method;
    private String path;
    private Protocol protocol;
    private String version;

    private RequestLine() {
    }

    public static RequestLine parse(String input) {
        return new RequestLine();
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
