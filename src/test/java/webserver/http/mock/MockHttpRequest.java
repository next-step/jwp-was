package webserver.http.mock;

import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpSessionManager;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.util.Collections;
import java.util.Map;

import static webserver.http.HttpMethod.GET;

public class MockHttpRequest extends HttpRequest {

    public MockHttpRequest(String path) {
        super(getMockRequestLine(path), getMockRequestHeader(Collections.emptyMap()), getMockRequestBody(), new HttpSessionManager());
    }

    public MockHttpRequest(String path, Map<String, String> header) {
        super(getMockRequestLine(path), getMockRequestHeader(Collections.emptyMap()), getMockRequestBody(), new HttpSessionManager());
    }

    private static RequestLine getMockRequestLine(String path) {
        return new RequestLine.Builder(GET, path, "HTTP/1.1").build();
    }

    private static RequestHeader getMockRequestHeader(Map<String, String> headers) {
        return RequestHeader.from(Collections.emptyList());
    }

    private static RequestBody getMockRequestBody() {
        return new RequestBody(new HttpParameter(Collections.emptyMap()));
    }


}
