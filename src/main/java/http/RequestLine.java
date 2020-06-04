package http;

import http.enums.Method;

public class RequestLine {


    private final Method method;
    private final String path;
    private  Protocol protocol2;

    public RequestLine(Method method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol2 = protocol;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol2.getProtocol();
    }

    public String getVersion() {
        return this.protocol2.getVersion();
    }
}
