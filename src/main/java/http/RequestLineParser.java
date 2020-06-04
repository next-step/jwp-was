package http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Args;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestLineParser {

    private static final String REQUEST_LINE_FORMAT = "(?<method>[A-Z]+) (?<uri>.+) (?<version>.+\\/.+)";
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile(REQUEST_LINE_FORMAT);
    protected static final String ILLEGAL_REQUEST_LINE = "유효하지 않은 요청입니다.";

    public static RequestLine parse(String requestLine) {
        Matcher requestLineMatcher = Args.checkPattern(REQUEST_LINE_PATTERN.matcher(requestLine), ILLEGAL_REQUEST_LINE);
        return new RequestLine(requestLineMatcher.group("method"),
            requestLineMatcher.group("uri"), new ProtocolVersion(requestLineMatcher.group("version")));
    }
}
