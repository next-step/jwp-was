package webserver.http;

import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import static java.util.Arrays.asList;
import static utils.IOUtils.readLines;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;
    private HttpParameter mergedHttpParameter;

    public static HttpRequest from(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        RequestLine requestLine = RequestLine.parse(reader.readLine());
        RequestHeader requestHeader = RequestHeader.from(readLines(reader));
        RequestBody requestBody = RequestBody.from(reader, requestHeader);

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.mergedHttpParameter = HttpParameter.of(asList(requestLine.getHttpParameter(), requestBody.getHttpParameter()));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public HttpParameter getMergedHttpParameter() {
        return mergedHttpParameter;
    }

    public String getResponseContentType() {
        return Optional.of(requestHeader)
                .map(RequestHeader::getAccept)
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");
    }

}
