package http;

import org.springframework.http.HttpMethod;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    private RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(String fullRequestLine) {
        String[] splitedRequestLine = fullRequestLine.split(" ");
        String[] splitedProtocol = splitedRequestLine[2].split("/");
        return new RequestLine(splitedRequestLine[0], splitedRequestLine[1], splitedProtocol[0], splitedProtocol[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }
}
