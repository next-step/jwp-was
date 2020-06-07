package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private String body;

    public HttpRequest(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();
        String line = br.readLine();
        this.requestLine = new RequestLine(line);
        logger.info(line);
        while (isNotEmpty(line)) {
            line = br.readLine();
            putHeader(header, line);
            logger.info(line);
        }
        this.header = new RequestHeader((header));
        this.body = IOUtils.readData(br, Integer.parseInt(header.getOrDefault(CONTENT_LENGTH, "0")));
        logger.info(body);
    }

    public RequestUrl findRequestUrl() {
        return RequestUrl.findByPath(requestLine.getPath());
    }

    public String getBody() {
        return body;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getHeader() {
        return header.getHeader();
    }

    public boolean isLogin() {
        Map<String, String> cookies = header.getCookies();
        String cookie = cookies.getOrDefault("logined", "false");
        return Boolean.parseBoolean(cookie);
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
