package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final BufferedReader br;
    private final RequestLine requestLine;
    private final Map<String, String> requestHeaders;


    public HttpRequest(BufferedReader br) {
        this.br = br;
        this.requestLine = RequestLine.of(getFirstLine());
        this.requestHeaders = processHeaders();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String header) {
        return requestHeaders.get(header);
    }

    private Map<String, String> processHeaders() {
        Map<String, String> headers = new HashMap<>();
        String line = null;
        try {
            line = br.readLine();
            while (!"".equals(line)) {
                logger.debug("header : {}", line);
                String[] headerValues = line.split(": ");
                if (headerValues.length == 2) {
                    headers.put(headerValues[0], headerValues[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return headers;
    }

    private String getFirstLine() {
        String line = null;
        try {
            line = br.readLine();
            logger.debug("request line : {}", line);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return line;
    }
}
