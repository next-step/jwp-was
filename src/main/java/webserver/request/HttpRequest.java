package webserver.request;

import utils.IOUtils;
import webserver.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final RequestBody body;

    public HttpRequest(final InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        this.requestLine = convertToRequestLine(br);
        this.headers = convertToRequestHeaders(br);
        this.body = convertToRequestBody(br);
    }

    private RequestLine convertToRequestLine(final BufferedReader br) throws IOException {
        return RequestLine.parse(br.readLine());
    }

    private RequestHeaders convertToRequestHeaders(final BufferedReader br) throws IOException {
        final RequestHeaders.Builder builder = RequestHeaders.builder();

        String headerNameAndValue = br.readLine();
        while (!"".equals(headerNameAndValue)) {
            builder.add(headerNameAndValue);
            headerNameAndValue = br.readLine();
        }

        return builder.build();
    }

    private RequestBody convertToRequestBody(final BufferedReader br) throws IOException {
        if (this.headers.hasRequestBody()) {
            final String requestBody = IOUtils.readData(br, this.headers.getContentLength());
            return new RequestBody(requestBody);
        }

        return null;
    }

    public RequestLine getRequestLine() {
        return this.requestLine;
    }

    public RequestHeaders convertToRequestHeaders() {
        return this.headers;
    }

    public RequestBody getBody() {
        return this.body;
    }

    public HttpMethod getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getCookie(final String cookieName) {
        return Arrays.stream(this.headers.get("Cookie").split("; "))
                .filter(it -> it.startsWith(cookieName + "="))
                .findFirst()
                .map(it -> it.split("=")[1])
                .orElseThrow(() -> new IllegalArgumentException("Cannot get Cookie: " + cookieName));
    }
}
