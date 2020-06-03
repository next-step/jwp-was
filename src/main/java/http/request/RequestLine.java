package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.HttpMethod;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Getter
public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private String method;
    private String path;
    private Protocol protocol;
    private Map<String, String> queryString = new HashMap<>();

    public static RequestLine of(String requestLine) {
        if (StringUtils.isEmpty(requestLine)) {
            throw new IllegalArgumentException();
        }

        String[] splitLine = requestLine.split(" ");

        if (splitLine.length != 3) {
            throw new IllegalArgumentException();
        }

        String[] protocolAndVersion = splitLine[2].split("/");
        return new RequestLine(splitLine[0], splitLine[1], new Protocol(protocolAndVersion[0], protocolAndVersion[1]))
    }

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        setPathAndQueryString(path);
        this.protocol = protocol;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("method는 ").append(method.name()).append("\r\n");
        sb.append("path는 ").append(path).append("\r\n");
        sb.append("protocol은 ").append(protocol).append("\r\n");
        sb.append("version은 ").append(version);

        return sb.toString();
    }
}
