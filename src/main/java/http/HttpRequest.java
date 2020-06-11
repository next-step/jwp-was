package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private static final String CHAR_SET = "UTF-8";

    private final RequestLine requestLine;
    private final HttpHeaders requestHeaders;
    private final QueryString queryString;


    public HttpRequest(BufferedReader br) {
        this.requestLine = RequestLine.of(getFirstLine(br));
        this.requestHeaders = processHeaders(br);
        this.queryString = QueryString.of(getQuery(br));
    }

    private String getFirstLine(BufferedReader br) {
        String line = null;
        try {
            line = br.readLine();
            logger.debug("request line : {}", line);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return line;
    }

    private String getQuery(BufferedReader br) {
        int contentLength = requestHeaders.getContentLength();
        return readQueryFromBody(br, contentLength);
    }

    private String readQueryFromBody(BufferedReader br, int contentLength) {
        try {
            return URLDecoder.decode(IOUtils.readData(br, contentLength), CHAR_SET);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
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
        return requestHeaders.getHeader(header);
    }

    private HttpHeaders processHeaders(BufferedReader br) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String line;
        try {
            line = br.readLine();
            while (line != null && !line.equals("") ) {
                logger.debug("header : {}", line);
                httpHeaders.addHeader(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return httpHeaders;
    }
}
