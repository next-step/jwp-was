package webserver.request;

import error.NotHttpMethodConstantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constant.HttpMethod;

import java.util.Objects;

public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
    private final HttpMethod method;
    private final Uri uri;
    private final ProtocolAndVersion protocolAndVersion;

    public RequestLine(String requestLine) {
        String[] requestLineElements = requestLine.split(" ");
        this.method = stringToHttpMethod(requestLineElements[0]);
        this.uri = new Uri(requestLineElements[1]);
        this.protocolAndVersion = new ProtocolAndVersion(requestLineElements[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Uri getUri() {
        return uri;
    }

    public ProtocolAndVersion getProtocolAndVersion() {
        return protocolAndVersion;
    }

    public HttpMethod stringToHttpMethod(String method) {
        if (Objects.equals(method, "POST")) {
            return HttpMethod.POST;
        } else if (Objects.equals(method, "GET")) {
            return HttpMethod.GET;
        } else {
            throw new NotHttpMethodConstantException();
        }
    }
}
