package webserver.http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestParams httpRequestParams;

    private HttpRequest(BufferedReader br) throws IOException {
        httpRequestLine = HttpRequestLine.of(br.readLine());
        httpRequestHeader = HttpRequestHeader.of(br);
        initHttpRequestParams(br);
    }

    private void initHttpRequestParams(BufferedReader br) throws IOException {
        if (httpRequestLine.withQueryString()) {
            httpRequestParams = HttpRequestParams.of(httpRequestLine.getQueryString());
            return;
        }

        if (HttpMethod.POST.match(httpRequestLine.getMethod())) {
            String params = IOUtils.readData(br, httpRequestHeader.getContentLength());
            httpRequestParams = HttpRequestParams.of(params);
        }
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        return new HttpRequest(br);
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public HttpRequestParams getHttpRequestParams() {
        return httpRequestParams;
    }

    public String getRequestUri() {
        return httpRequestLine.getRequestUri();
    }
}