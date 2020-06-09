package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String CONTENT_LENGTH = "Content-Length";
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String line = br.readLine();
        parsingHeader(br, header, line);
        this.requestLine = new RequestLine(line);
        this.header = new RequestHeader(header);
        this.body = new RequestBody(parsingBody(br, header));
    }

    public String getBody() {
        return body.getBody();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public boolean isLogin() {
        String cookie = header.getCookie("logined");
        if (StringUtils.hasText(cookie)) {
            return Boolean.parseBoolean(cookie);
        }

        return false;
    }

    private void parsingHeader(BufferedReader br, Map<String, String> header, String line) throws IOException {
        while (isNotEmpty(line)) {
            line = br.readLine();
            putHeader(header, line);
        }
    }

    private String parsingBody(BufferedReader br, Map<String, String> header) throws IOException {
        return IOUtils.readData(br, Integer.parseInt(header.get(CONTENT_LENGTH)));
    }

    public RequestUrl findRequestUrl() {
        return RequestUrl.findByPath(requestLine.getPath());
    }

    public boolean isStylesheet() {
        return StyleSheet.isContain(getPath());
    }

    private boolean isNotEmpty(String line) {
        return line != null && !"".equals(line);
    }

    private void putHeader(Map<String, String> header, String line) {
        String[] values = line.split(":");
        if (values.length > 1) {
            header.put(values[0], values[1].trim());
        }
    }
}
