package http;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpHeaders {

    private static final String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";

    private static final Logger logger = LoggerFactory.getLogger(HttpHeaders.class);

    private Map<String, String> requestHeaders = new HashMap<>();

    public void addHeader(String line) {
        logger.debug("header : {}", line);
        String[] headerValues = line.split(HEADER_DELIMITER);
        requestHeaders.put(headerValues[0].trim(), headerValues[1].trim());
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public int getContentLength() {
        String contentLength = requestHeaders.get(CONTENT_LENGTH_KEY);
        return contentLength == null ? 0 : Integer.parseInt(contentLength);
    }
}
