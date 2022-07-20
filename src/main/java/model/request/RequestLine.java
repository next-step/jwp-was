package model.request;

import webserver.http.Method;
import webserver.http.Protocol;
import webserver.http.Type;
import webserver.http.Version;

public class RequestLine {

    private static final String DELIMITER = " ";
    private static final int INDEX_METHOD = 0;
    private static final int INDEX_PROTOCOL = 2;
    private final Method method;
    private final Protocol protocol;

    public RequestLine(Method method, Protocol protocol) {
        this.method = method;
        this.protocol = protocol;
    }

    public static RequestLine parse(String request) {
        String[] requestElements = request.split(DELIMITER);

        Method method = Method.from(requestElements[INDEX_METHOD]);
        Protocol protocol = new Protocol(requestElements[INDEX_PROTOCOL]);

        return new RequestLine(method, protocol);
    }

    public Method getMethod() {
        return method;
    }

    public Type getProtocolType() {
        return protocol.getType();
    }

    public Version getProtocolVersion() {
        return protocol.getVersion();
    }
}
