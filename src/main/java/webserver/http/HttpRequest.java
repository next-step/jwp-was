package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class HttpRequest {
    public static final String VALIDATION_MESSAGE = "잘못된 요청입니다.";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);
    private static final Pattern HEADER_DELIMITER = Pattern.compile(": ");
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("&?([^=&]+)=([^=&]+)");

    private final RequestLine requestLine;
    private final Headers headers;
    private final Attributes attributes;

    public HttpRequest(InputStream in) {
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = makeRequestLine(br);
        this.headers = new Headers(makeHeaders(br));
        this.attributes = new Attributes(makeAttributes(br));
        LOGGER.debug(requestLine.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) && Objects.equals(headers, that.headers) && Objects.equals(attributes.getAttributes(), that.attributes.getAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, headers, attributes.getAttributes());
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpProtocol getHttpProtocol() {
        return requestLine.getHttpProtocol();
    }

    public int getContentLength() {
        try {
            return Integer.parseInt((String) headers.getHeader("Content-Length"));
        } catch (Exception e) {
            return 0;
        }
    }

    public Map<String, Object> getAttributes() {
        return attributes.getAttributes();
    }

    public String getAttribute(String key) {
        return attributes.getAttribute(key);
    }

    public boolean getCookie(String key, Class<Boolean> returnType) {
        return headers.getCookie(key, returnType);
    }

    private RequestLine makeRequestLine(BufferedReader br) {
        final String line = readLine(br);
        validate(line);
        return new RequestLine(line);
    }

    private Map<String, Object> makeHeaders(BufferedReader br) {
        Map<String, Object> headers = new HashMap<>();
        String line;
        while (!"".equals(line = readLine(br))) {
            final String[] splitLine = HEADER_DELIMITER.split(line);
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }

    private String readLine(BufferedReader br) {
        String line;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE, e);
        }
        return line;
    }

    private void validate(String line) {
        if (line == null) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private Map<String, Object> makeAttributes(BufferedReader br) {
        if (requestLine.isGet()) {
            return new HashMap<>();
        }

        try {
            final int contentLength = getContentLength();
            final String payload = IOUtils.readData(br, contentLength);
            return HttpUtils.parseParameters(payload, ATTRIBUTE_PATTERN);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
