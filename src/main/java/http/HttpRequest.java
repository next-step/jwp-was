package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final BufferedReader br;
    private final RequestLine requestLine;
    private final Map<String, String> requestHeaders;

    private final QueryString queryString;


    public HttpRequest(BufferedReader br) {
        this.br = br;
        this.requestLine = RequestLine.of(getFirstLine());
        this.requestHeaders = processHeaders();
        this.queryString = QueryString.of(getQuery());
    }

    private String getQuery() {
        String query = null;
        String contentLength = requestHeaders.get("Content-Length");

        if (contentLength != null) {
            try {
                query = URLDecoder.decode(IOUtils.readData(br, Integer.parseInt(contentLength)), "UTF-8");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return query;
    }

    public String getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getParameter(String key) {
        return queryString.getPrameter(key);
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
            while (!"".equals(line) && line != null) {
                logger.debug("header : {}", line);
                String[] headerValues = line.split(": ");
                if (headerValues.length == 2) {
                    headers.put(headerValues[0].trim(), headerValues[1].trim());
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
