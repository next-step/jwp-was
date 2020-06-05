package http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Args;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLine {

    private static final String REQUEST_LINE_FORMAT = "(?<method>[A-Z]+) (?<uri>.+) (?<version>.+/.+)";
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile(REQUEST_LINE_FORMAT);
    protected static final String ILLEGAL_REQUEST_LINE = "유효하지 않은 요청입니다.";

    private final HttpMethod httpMethod;
    private final RequestURI requestURI;
    private final ProtocolVersion protocolVersion;

    public static RequestLine of(String requestLineInput) {
        Matcher requestLineMatcher = Args.checkPattern(REQUEST_LINE_PATTERN.matcher(requestLineInput), ILLEGAL_REQUEST_LINE);
        return new RequestLine(requestLineMatcher.group("method"),
            requestLineMatcher.group("uri"), new ProtocolVersion(requestLineMatcher.group("version")));
    }

    public RequestLine(String httpMethod, String path, ProtocolVersion protocolVersion) {
        this.httpMethod = HttpMethod.of(httpMethod);
        this.requestURI = new RequestURI(path);
        this.protocolVersion = protocolVersion;
    }

    public String getHttpMethod() {
        return this.httpMethod.name();
    }

    public String getPath() {
        return this.requestURI.getPath();
    }

    public String getQueryString() {
        return this.requestURI.getQueryString();
    }

    public String getProtocolVersion() {
        return this.protocolVersion.getHttpProtocol();
    }

    public String getVersion() {
        return this.protocolVersion.getVersion();
    }
}
