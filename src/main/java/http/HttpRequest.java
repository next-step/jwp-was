package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static http.RequestHeaders.HEADER_SEPARATOR;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders = new RequestHeaders();
    private RequestBody requestBody = new RequestBody();
    private QueryStrings queryStrings;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String requestLineString = bufferedReader.readLine();
        requestLine = RequestLineParser.parse(requestLineString);

        String readLine = bufferedReader.readLine();
        while (!"".equals(readLine)) {
            System.out.println(readLine);
            parseHttpHeader(readLine);
            readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
        }
        if (requestHeaders.hasContentLength()) {
            String body = IOUtils.readData(bufferedReader, Integer.parseInt(requestHeaders.getContentLength()));
            requestBody = new RequestBody(body);
        }
        buildQueryStrings();
    }

    private void buildQueryStrings() {
        QueryStrings queryStrings = new QueryStrings();
        if (!"".equals(requestBody.getRequestBody())) {
            logger.debug("requestBody not null");
            queryStrings.buildQueryStrings(requestBody.getRequestBody());
        }
        if (!"".equals(requestLine.getQuery())) {
            logger.debug("queryParameter not null");
            queryStrings.buildQueryStrings(requestLine.getQuery());
        }
        this.queryStrings = queryStrings;
    }

    private void parseHttpHeader(final String readLine) {
        if (readLine.contains(HEADER_SEPARATOR)) {
            requestHeaders.addHeader(readLine);
        }
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getParameter(final String key) {
        return queryStrings.getValue(key);
    }

    public String getMethod() {
        return requestLine.getMethod().toString();
    }

    public String getHeader(final String header) {
        return requestHeaders.getHeader(header);
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(Method.GET);
    }

    public String getPath() {
        return requestLine.getRequestPath();
    }

    public String getCookie() {
        return requestHeaders.getCookie();
    }

    public HttpSession getSession(final HttpResponse httpResponse) {
        Optional<String> sessionIdOptional = requestHeaders.getSessionId();
        if (!sessionIdOptional.isPresent()) {
            HttpSession httpSession = HttpSessions.addHttpSession();
            httpResponse.setSessionId(httpSession.getId());
            return httpSession;
        }
        return HttpSessions.getHttpSession(sessionIdOptional.get());
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", requestBody=" + requestBody +
                ", queryStrings=" + queryStrings +
                '}';
    }

}

