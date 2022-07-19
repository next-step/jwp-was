package webserver.domain;

import org.springframework.http.HttpMethod;

public class RequestLine {
    public static final String DELIMITER = " ";
    public static final String PROTOCOL_SPLIT_DELIMITER = "/";

    private HttpMethod method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(){}

    public RequestLine(HttpMethod method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(String line) {
        String[] attributes = line.split(DELIMITER);
        String[] protocolAndVersion = attributes[2].split(PROTOCOL_SPLIT_DELIMITER);

        return new RequestLine(HttpMethod.valueOf(attributes[0]),
                attributes[1],
                protocolAndVersion[0],
                protocolAndVersion[1]);
    }

    public HttpMethod getMethod() {
        return method;
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
}
