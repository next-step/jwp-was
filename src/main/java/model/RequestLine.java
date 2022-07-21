package model;

import constant.HttpMethod;

import java.util.Map;

public class RequestLine {
    public static final String DELIMITER = " ";

    private HttpMethod method;
    private String path;
    private QueryString queryString;
    private String protocol;
    private String version;

    public RequestLine() { }

    public RequestLine(HttpMethod method, String path, QueryString queryString, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public static RequestLine from(String line) {
        System.out.println(line);
        String[] attributes = line.split(DELIMITER);
        HttpPath httpPath = HttpPath.Instance(attributes[1]);
        HttpProtocol httpProtocol = HttpProtocol.Instance(attributes[2]);

        return new RequestLine(
                HttpMethod.of(attributes[0]),
                httpPath.getPath(),
                httpPath.getQueryString(),
                httpProtocol.getProtocol(),
                httpProtocol.getVersion()
        );
    }

    @Override
    public String toString() {
        return "method=" + method +
                ", path='" + path + '\'' +
                ", queryString=" + queryString +
                ", protocol='" + protocol + '\'' +
                ", version='" + version + "'";
    }
}
