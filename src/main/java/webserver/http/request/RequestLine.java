package webserver.http.request;

import webserver.http.protocol.Protocol;

import java.nio.charset.Charset;

public class RequestLine {
    private final Method method;
    private final URI uri;
    private final Protocol protocol;

    public RequestLine(Method method, URI uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public boolean hasMethod(Method method) {
        return this.method.is(method);
    }

    public String getPath() {
        return uri.getPath();
    }

    public String getParameter(String key) {
        return uri.getParameter(key);
    }

    public void addParameters(Parameters parameters) {
        uri.addParameters(parameters);
    }

    public void decodeCharacter(Charset charset) {
        uri.decodeCharacter(charset);
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
