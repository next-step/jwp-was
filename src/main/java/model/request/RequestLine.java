package model.request;

import enums.HttpMethod;
import model.WebProtocol;
import utils.parser.WebProtocolParser;

import java.util.regex.Pattern;

public class RequestLine {
    private static final Pattern REQUEST_LINE = Pattern.compile("[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+");
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final Integer METHOD_INDEX = 0;
    public static final Integer PATH_INDEX = 1;
    public static final Integer PROTOCOL_WITH_VERSION_INDEX = 2;

    private final HttpMethod httpMethod;
    private final Path path;
    private final WebProtocol webProtocol;

    public RequestLine(String requestLine) {
        validateCanParsing(requestLine);

        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);
        String method = split[METHOD_INDEX];
        String path = split[PATH_INDEX];

        WebProtocol webProtocol = WebProtocolParser.parse(split[PROTOCOL_WITH_VERSION_INDEX]);

        this.httpMethod = HttpMethod.getHttpMethod(method);
        this.webProtocol = webProtocol;
        this.path = new Path(path);
    }

    private void validateCanParsing(String requestLine) throws IllegalArgumentException {
        if (!isRequestLinePattern(requestLine)) {
            throw new IllegalArgumentException("Request Line이 올바른 형식을 가지고 있지 않습니다.");
        }
    }

    private boolean isRequestLinePattern(String requestLine) {
        return REQUEST_LINE.matcher(requestLine).matches();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Boolean hasQueryString() {
        return path.hasQueryString();
    }

    public String getPath() {
        return path.getPath();
    }

    public String getParameter(String key) {
        return path.getParameter(key);
    }

    public WebProtocol getWebProtocol() {
        return webProtocol;
    }
}