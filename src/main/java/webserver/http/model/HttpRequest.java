package webserver.http.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        this.requestLine = new RequestLine(line);
        this.requestHeaders = new RequestHeaders(bufferedReader, line);
        this.requestBody = null;
        if (Method.isPost(requestLine.getMethod())) { // FIXME getter 대신 메시지
            this.requestBody = new RequestBody(bufferedReader, requestHeaders);
        }
//        new HttpRequest(requestLine, requestHeaders, requestBody); // TODO 왜 호출이 안될까?
    }

    public HttpRequest(String httpRequestText) {
        List<String> textList = Arrays.asList(httpRequestText.split("\n"));
        List<List<String>> requestInformation = requestHeaderAndBody(textList);

        this.requestLine = new RequestLine(textList.get(0));
        this.requestHeaders = new RequestHeaders(requestInformation.get(0));
        this.requestBody = null;
        if (Method.isPost(requestLine.getMethod())) {
            this.requestBody = new RequestBody(requestInformation.get(1));
        }
    }

    private List<List<String>> requestHeaderAndBody(List<String> textList) {
        return new ArrayList<>(textList.stream().filter(text -> !text.equals(textList.get(0)))
                .collect(Collectors.partitioningBy(line -> textList.indexOf(line) > textList.indexOf(""))).values());
    }

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public boolean isStaticResource() {
        return requestLine.isStaticResource();
    }

    public String responsePath() {
        return requestLine.fullPath();
    }

    public QueryStrings getQueryStrings() {
        return requestLine.getQueryStrings();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Method getMethod() {
        return requestLine.getMethod();
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders.getRequestHeadersMap();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}