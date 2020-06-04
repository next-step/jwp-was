package http;

import org.springframework.util.MultiValueMap;

public class RequestLine {

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private Parameters parameters;

    private RequestLine(HttpMethod method, String path, Parameters parameters, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.protocol = protocol;
    }

    public static RequestLine from(String requestLine) {
        String[] tokens = requestLine.split(" ");
        String[] pathAndQueryString= tokens[1].split("\\?");

        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        String path = pathAndQueryString[0];
        Parameters parameters = Parameters.emptyParameters();
        Protocol protocol = Protocol.fromString(tokens[2]);

        boolean hasQueryString = pathAndQueryString.length > 1;

        if(hasQueryString) {
            parameters = Parameters.from(pathAndQueryString[1]);
        }

        return new RequestLine(method, path, parameters, protocol);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return this.protocol.getProtocol();
    }

    public String getProtocolVersion() {
        return this.protocol.getVersion();
    }

    public String getVersion() {
        return this.protocol.getVersion();
    }

    public MultiValueMap<String, String> getParameterMap() {
        return this.parameters;
    }

    public String getParameter(String key) {
        return this.parameters.getParameter(key);
    }

}
