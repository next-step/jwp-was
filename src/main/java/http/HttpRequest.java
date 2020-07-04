package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static http.RequestHeaders.HEADER_SEPARATOR;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders = new RequestHeaders();
    private RequestBody requestBody = new RequestBody();

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String requestLine = bufferedReader.readLine();
        this.requestLine = RequestLineParser.parse(requestLine);
        String readLine = bufferedReader.readLine();
        while (!"".equals(readLine)) {
            System.out.println(readLine);
            parseHttpHeader(readLine);
            readLine = bufferedReader.readLine();
            if (readLine == null) {
                return;
            }
        }

        if (requestHeaders.hasContentLength()) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(requestHeaders.getContentLength()));
            this.requestBody = new RequestBody(body);
        }
    }

    private void parseHttpHeader(final String readLine) {
        if (readLine.contains(HEADER_SEPARATOR)) {
            requestHeaders.addHeader(readLine);
        }
    }

    public String getMethod() {
        return requestLine.getMethod().toString();
    }

    public String getHeader(final String header) {
        return requestHeaders.getHeader(header);
    }

    public String getParameter(final String key) {
        return requestLine.getQueryValue(key);
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(Method.GET);
    }

    public String getHandler() {
        return requestLine.getHandlerPath();
    }

    public String getPath() {
        return requestLine.getRequestPath();
    }

    public Map<String, String> getRequestBody() {
        return requestBody.getRequestBody();
    }

    public String getCookie() {
        return requestHeaders.getCookie();
    }

    public String getAccept() {
        return requestHeaders.getAccept();
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", requestBody=" + requestBody +
                '}';
    }

}

