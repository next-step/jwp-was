package webserver.http.domain.request;

import webserver.http.domain.Protocol;
import webserver.http.domain.exception.BadRequestException;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER_REGEX = " ";

    private static final int REQUEST_LINE_SPLIT_SIZE = 3;
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

    public Method getMethod() {
        return method;
    }

    public static RequestLine from(String message) {
        String[] splitMessage = message.split(REQUEST_LINE_DELIMITER_REGEX);
        if (splitMessage.length != REQUEST_LINE_SPLIT_SIZE) {
            throw new BadRequestException(String.format("'[Method] [URI] [Protocol]' 형식의 requestLine 메시지가 아닙니다. {message=%s}", message));
        }

        String method = splitMessage[0];
        String uri = splitMessage[1];
        String protocol = splitMessage[2];

        return new RequestLine(
                Method.from(method),
                URI.from(uri),
                Protocol.from(protocol)
        );
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
