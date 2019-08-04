package webserver;

import utils.IOUtils;
import webserver.request.HttpMethod;
import webserver.request.RequestBody;
import webserver.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {

    private static final String UTF_8 = "UTF-8";

    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private RequestBody requestBody;

    private Request(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    static Request of(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
        HttpHeaders httpHeaders = parseHeaders(bufferedReader);

        if (httpHeaders.getContentLength() > -1) {
            String requestBody = IOUtils.readData(bufferedReader, httpHeaders.getContentLength());
            return new Request(requestLine, httpHeaders, RequestBody.parse(requestBody));
        }
        return new Request(requestLine, httpHeaders, RequestBody.EMPTY);
    }

    private static HttpHeaders parseHeaders(BufferedReader bufferedReader) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerInfo;
        while ((headerInfo = bufferedReader.readLine()) != null) {
            if (headerInfo.isEmpty()) {
                break;
            }
            httpHeaders.add(headerInfo);
        }
        return httpHeaders;
    }

    String getParameter(String field) {
        return requestBody.get(field);
    }

    boolean matchPath(String path) {
        return requestLine.matchPath(path);
    }

    public boolean isGet() {
        return HttpMethod.GET == requestLine.getMethod();
    }

    String getPath() {
        return requestLine.getPath();
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", httpHeaders=" + httpHeaders +
                ", requestBody=" + requestBody +
                '}';
    }
}
