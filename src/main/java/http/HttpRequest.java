package http;

import http.exception.HttpHeaderRegistrationException;
import http.method.HttpMethod;
import http.requestline.RequestLine;
import http.requestline.RequestLineParser;
import http.requestline.path.Path;
import org.springframework.util.StringUtils;

public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ":";
    private static final int HEADER_TOKEN_SIZE = 2;

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;

    public HttpRequest(String requestLine) {
        this.requestLine = RequestLineParser.parse(requestLine);
        this.httpHeaders = new HttpHeaders();
    }

    public void registerHeader(String headerLine) {
        if (StringUtils.isEmpty(headerLine)) {
            return;
        }

        if (!headerLine.contains(HTTP_HEADER_DELIMITER)) {
            throw new HttpHeaderRegistrationException("Does not have delimiter ':'");
        }

        String[] tokens = headerLine.split(HTTP_HEADER_DELIMITER, HEADER_TOKEN_SIZE);
        httpHeaders.put(tokens[0].trim(), tokens[1].trim());
    }

    public boolean hasPathFileExtension() {
        return requestLine.hasPathFileExtension();
    }

    public String getFilePath() {
        return requestLine.getFilePath();
    }

    public String getHeader(String headerKey) {
        return httpHeaders.get(headerKey);
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getMethod();
    }

    public Path getPath() {
        return requestLine.getPath();
    }

    public String getMimeType() {
        return requestLine.getMimeType();
    }
}
