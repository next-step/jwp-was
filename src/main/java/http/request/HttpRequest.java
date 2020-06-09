package http.request;

import http.request.exception.HttpHeaderRegistrationException;
import http.request.method.HttpMethod;
import http.request.querystring.QueryString;
import http.request.requestline.RequestLine;
import http.request.requestline.RequestLineParser;
import http.request.requestline.path.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpRequest {

    private static final String HTTP_HEADER_DELIMITER = ":";
    private static final int HEADER_TOKEN_SIZE = 2;

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private QueryString body = new QueryString("");

    HttpRequest(String requestLine) {
        this.requestLine = RequestLineParser.parse(requestLine);
        this.httpHeaders = new HttpHeaders();
    }

    public static HttpRequest from(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line = bufferedReader.readLine();
        HttpRequest httpRequest = new HttpRequest(line);

        while (!StringUtils.isEmpty(line)) {
            log.debug(line);
            line = bufferedReader.readLine();
            httpRequest.registerHeader(line);
        }

        String contentLength = httpRequest.getHeader("Content-Length");
        if (doesNotHaveContentLength(contentLength)) {
            return httpRequest;
        }

        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        log.debug(body);
        httpRequest.registerBody(body);

        return httpRequest;
    }

    private static boolean doesNotHaveContentLength(String contentLength) {
        return StringUtils.isEmpty(contentLength) || "0".equals(contentLength);
    }

    void registerHeader(String headerLine) {
        if (StringUtils.isEmpty(headerLine)) {
            return;
        }

        if (!headerLine.contains(HTTP_HEADER_DELIMITER)) {
            throw new HttpHeaderRegistrationException("Does not have delimiter ':'");
        }

        String[] tokens = headerLine.split(HTTP_HEADER_DELIMITER, HEADER_TOKEN_SIZE);
        httpHeaders.put(tokens[0].trim(), tokens[1].trim());
    }

    void registerBody(String body) {
        if (StringUtils.isEmpty(body)) {
            return;
        }

        this.body = new QueryString(body);
    }

    public String getBody(String key) {
        return body.get(key);
    }

    public boolean hasPathFileExtension() {
        return requestLine.hasPathFileExtension();
    }

    public String getFilePath() {
        return requestLine.getFilePath();
    }

    public String getQueryStringValue(String key) {
        return requestLine.getQueryStringValue(key);
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
