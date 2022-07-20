package webserver;

import error.NotHttpMethodConstantException;
import org.springframework.http.HttpMethod;import java.util.HashMap;

import java.util.Objects;

public class RequestLineParser {
    private final HttpMethod method;
    private final Uri uri;
    private final ProtocolAndVersion protocolAndVersion;

    public RequestLineParser(String requestLine) {
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
