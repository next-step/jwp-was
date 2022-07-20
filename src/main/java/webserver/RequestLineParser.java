package webserver;

import error.NotHttpMethodConstantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;import java.util.HashMap;

import java.util.Objects;

public class RequestLineParser {
    private static final Logger log = LoggerFactory.getLogger(RequestLineParser.class);
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
            log.info("POST요청입니다.");
            return HttpMethod.POST;
        } else if (Objects.equals(method, "GET")) {
            log.info("GET요청입니다.");
            return HttpMethod.GET;
        } else {
            throw new NotHttpMethodConstantException();
        }
    }
}
