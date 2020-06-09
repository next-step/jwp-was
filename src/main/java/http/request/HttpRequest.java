package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String CONTENT_LENGTH = "Content-Length";
    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
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

    public String getParameter(String key) {
        if (requestLine.isPost()) {
            return body.getParameter(key);
        }
        return requestLine.getParameter(key);
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
        if (requestLine.isPost()) {
            return IOUtils.readData(br, Integer.parseInt(header.get(CONTENT_LENGTH)));
        }

        return "";
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
        if (StringUtils.isEmpty(line)) {
            return;
        }
        String[] values = line.split(": ");
        if (values.length > 1) {
            header.put(values[0], values[1]);
        }
    }
}
