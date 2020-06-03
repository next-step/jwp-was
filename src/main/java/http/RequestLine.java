package http;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLine {

    private final String method;
    private final Method method1;
    private final Protocol protocol;
    private final URL url;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.protocol = protocol;
        this.url = new URL(path);
        this.method1 = Method.valueOf(method);
    }

    public String getMethod() {
        return this.method;
    }

    public String getMethod1() {
        return this.method1.toString();
    }

    public String getPath() {
        return this.url.getPath();
    }

    public String getProtocol() {
        return this.protocol.getProtocol();
    }

    public String getVersion() {
        return this.protocol.getVersion();
    }

    public String getQueryString() {
        return this.url.getQueryString();
    }
}
