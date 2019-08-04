package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private RequestLine requestLine;
    private Map<String, String> requestHeaders;
    private Map<String, String> cookies;
    private QueryParam queryParam = QueryParam.EMPTY_QUERY_PARAM;

    public Request(BufferedReader br) throws IOException {
        requestHeaders = new HashMap<>();
        cookies = new HashMap<>();
        initialize(br);
    }

    private void initialize(BufferedReader br) throws IOException {
        String line = br.readLine();
        requestLine = RequestLine.parse(line);

        while (!(line = br.readLine()).isEmpty()) {
            String[] keyValue = line.split(": ");
            requestHeaders.put(keyValue[0], keyValue[1]);
        }

        if(requestHeaders.get("Cookie") != null) {
            Arrays.stream(requestHeaders.get("Cookie").split(";"))
                    .map(String::trim)
                    .map(s -> s.split("="))
                    .forEach(strings -> cookies.put(strings[0], strings[1]));
        }

        String contentLength = requestHeaders.get("Content-Length");
        if(contentLength != null) {
            String messageBody = IOUtils.readData(br, Integer.parseInt(requestHeaders.get("Content-Length")));
            queryParam = QueryParam.parse(messageBody);
        }
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getBodyRequest(String key) {
        return queryParam.getParameter(key);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", cookies=" + cookies +
                ", queryParam=" + queryParam +
                '}';
    }
}
