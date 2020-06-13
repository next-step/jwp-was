package http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Args;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLine {

    private static final String REQUEST_LINE_FORMAT = "(?<method>[A-Z]+) (?<uri>.+) (?<version>.+/.+)";
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile(REQUEST_LINE_FORMAT);
    protected static final String ILLEGAL_REQUEST_LINE = "유효하지 않은 요청입니다.";

    private final HttpMethod method;
    private final RequestURI requestURI;
    private final ProtocolVersion protocolVersion;

    private RequestLine(String method, String uri, String protocolVersion) {
        this.method = HttpMethod.of(method);
        this.requestURI = new RequestURI(uri);
        this.protocolVersion = new ProtocolVersion(protocolVersion);
    }

    public static RequestLine of(String line) {
        Matcher requestLineMatcher = Args.checkPattern(REQUEST_LINE_PATTERN.matcher(line), ILLEGAL_REQUEST_LINE);
        return new RequestLine(requestLineMatcher.group("method"),
            requestLineMatcher.group("uri"), requestLineMatcher.group("version"));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return requestURI.getPath();
    }

    public QueryString getQueryString() {
        return requestURI.getQueryString();
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
