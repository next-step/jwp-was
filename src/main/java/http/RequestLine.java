package http;

import http.enums.Method;

public class RequestLine {


    private final Method method;
    private final PathAndQueryString pathAndQueryString;
    private  Protocol protocol;

    public RequestLine(Method method, PathAndQueryString pathAndQueryString, Protocol protocol) {
        this.method = method;
        this.pathAndQueryString = pathAndQueryString;
        this.protocol = protocol;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.pathAndQueryString.getPath();
    }

    public String getParameter(String name) {
        return this.pathAndQueryString.getQueryString().getParameter(name);
    }
    public String getProtocol() {
        return this.protocol.getProtocol();
    }

    public String getVersion() {
        return this.protocol.getVersion();
    }
}
