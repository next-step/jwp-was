package utils;

import model.HttpMethodType;

public class RequestLine {

    public static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PROTOCOL_AND_VERSION_INDEX = 2;
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;
    public static final String REGEX_BLANK = " ";
    public static final String REGEX_SLASH = "/";

    private HttpMethodType method;
    private String path;
    private String protocol;
    private String version;

    public RequestLine(String requestLine) {
        this.method = parsingMethod(requestLine);
        this.path = parsingPath(requestLine);
        this.protocol = parsingProtocol(requestLine);
        this.version = parsingVersion(requestLine);
    }

    private HttpMethodType parsingMethod(String requestLine) {
        return HttpMethodType.getType(
                requestLine.split(REGEX_BLANK)[METHOD_INDEX]);
    }

    private String parsingPath(String requestLine) {
        return requestLine.split(REGEX_BLANK)[PATH_INDEX];
    }

    private String parsingProtocol(String requestLine) {
        return requestLine.split(REGEX_BLANK)[PROTOCOL_AND_VERSION_INDEX]
                .split(REGEX_SLASH)[PROTOCOL_INDEX];
    }

    private String parsingVersion(String requestLine) {
        return requestLine.split(REGEX_BLANK)[PROTOCOL_AND_VERSION_INDEX]
                .split(REGEX_SLASH)[VERSION_INDEX];
    }

    public HttpMethodType getMethod() {
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
