package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.HttpMethod;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private String protocol;
    private String version;
    private Map<String, String> queryString = new HashMap<>();

    public String getMethod() {
        return Optional.ofNullable(method)
                .map(HttpMethod::name)
                .orElse(null);
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public RequestLine(String requestLine) {
        if (StringUtils.isEmpty(requestLine)) {
            throw new IllegalArgumentException();
        }

        String[] splitLine = requestLine.split(" ");

        if (splitLine.length < 3) {
            throw new IllegalArgumentException();
        }

        setMethod(splitLine[0]);
        setPathAndQueryString(splitLine[1]);
        setProtocolAndVersion(splitLine[2]);
    }

    void setMethod(String method) {
        this.method = HttpMethod.resolve(method);

        if (Objects.isNull(method) || StringUtils.isEmpty(getMethod())) {
            throw new IllegalArgumentException();
        }
    }

    void setPathAndQueryString(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }

        int queryStringBeginIndex = path.indexOf("?");

        if (queryStringBeginIndex < 0) {
            this.path = path;
            this.queryString = Collections.emptyMap();
        }
        else {
            this.path = path.substring(0, queryStringBeginIndex);
            this.queryString = buildQueryString(path.substring(queryStringBeginIndex + 1));
        }

        if (StringUtils.isEmpty(this.path)) {
            throw new IllegalArgumentException();
        }
    }

    Map<String, String> buildQueryString(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            throw new IllegalArgumentException();
        }

        return Arrays.stream(queryString.split("&"))
                .filter(param -> param.contains("="))
                .map(param -> param.split("="))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    void setProtocolAndVersion(String param) {
        String[] protocolAndVersion = param.split("/");

        if (protocolAndVersion.length < 2) {
            throw new IllegalArgumentException();
        }

        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];

        if (StringUtils.isEmpty(this.protocol) || StringUtils.isEmpty(this.version)) {
            throw new IllegalArgumentException();
        }
    }
}
