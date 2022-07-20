package model;

import exception.HttpMethodExceptionMessage;
import exception.NotFoundHttpMethodException;

import static utils.DelimiterConstants.*;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private String parameters;
    private String protocol;
    private String version;

    private RequestLine(HttpMethod method, String path, String parameters, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parsing(String firstLine) {
        String[] requestDataArray = firstLine.split(SPACE);
        HttpMethod method = HttpMethod.of(requestDataArray[0])
                .orElseThrow( () -> new NotFoundHttpMethodException(HttpMethodExceptionMessage.NOT_FOUND_MESSAGE));
        String path = requestDataArray[1];
        String parameters = "";
        if (hasParameters(method, path)) {
            String[] splitPath = path.split(QUERY_STRING_DELIMITER);
            path = splitPath[0];
            parameters = splitPath[1];
        }
        String[] protocolAndVersion = requestDataArray[2].split(SLASH);
        return new RequestLine(method, path, parameters, protocolAndVersion[0], protocolAndVersion[1]);
    }

    private static boolean hasParameters(HttpMethod method, String path) {
        return HttpMethod.GET.equals(method) && path.split(QUERY_STRING_DELIMITER).length >= 2;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameters() {
        return parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
