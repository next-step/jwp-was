package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private RequestLine requestLine;
    private HttpHeaders headers;
    private RequestBody requestBody;

    public HttpRequest(InputStream in) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            requestLine = new RequestLine(URLDecoder.decode(reader.readLine(), UTF_8));
            headers = HttpHeaders.from(reader);
            requestBody = new RequestBody(URLDecoder.decode(IOUtils.readData(reader, headers.getContentLength()), UTF_8));
            logger.debug("request body = {}", requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getRequestURI() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        HttpMethod method = getMethod();
        if (method.isPost()) {
            return requestBody.getParameter(name);
        }
        return requestLine.getParameter(name);
    }
}
