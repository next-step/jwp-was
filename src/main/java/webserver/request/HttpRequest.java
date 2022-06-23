package webserver.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private static final String BLANK = " ";

    private final HttpMethod httpMethod;
    private final Uri uri;
    private final Protocol protocol;
    private final Headers headers;
    private final Body body;

    public HttpRequest(HttpMethod httpMethod, Uri uri, Protocol protocol, Headers headers, Body body) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(BufferedReader br) throws IOException {
        String[] lineSplit = br.readLine().split(BLANK);
        HttpMethod httpMethod = HttpMethod.from(lineSplit[0]);
        Uri uri = Uri.from(lineSplit[1]);
        Protocol protocol = Protocol.from(lineSplit[2]);
        Headers headers = Headers.from(br);
        int contentLength = headers.getContentLength();
        Body body = Body.from(IOUtils.readData(br, contentLength));
        return new HttpRequest(httpMethod, uri, protocol, headers, body);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpRequest)) return false;
        HttpRequest that = (HttpRequest) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uri, protocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Uri getUri() {
        return uri;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getPathStr() {
        return uri.getPathStr();
    }

    public boolean matchPath(String str) {
        return uri.matchPath(str);
    }

    public Headers getHeaders() {
        return headers;
    }

    public Body getBody() {
        return body;
    }

    public Map<String, String> getQueryMap() {
        return uri.getQueryMap();
    }
}
