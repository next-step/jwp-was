package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static http.RequestHeaders.HEADER_SEPARATOR;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders = new RequestHeaders();
    private RequestBody requestBody = new RequestBody();

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        this.requestLine = RequestLineParser.parse(bufferedReader.readLine());
        String readLine = bufferedReader.readLine();

        while (!"".equals(readLine)) {
            readLine = bufferedReader.readLine();
            parseHttpHeader(readLine);
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

    public boolean isGet() {
        return requestLine.getMethod().equals(Method.GET);
    }

    public String getHandler() {
        System.out.println("Handler Mapping getHandler: " + requestLine.getHandlerPath());
        return requestLine.getHandlerPath();
    }

    public String getPath() {
        return requestLine.getRequestPath();
    }

    public Map<String, String> getRequestBody() {
        return requestBody.getRequestBody();
    }
}

