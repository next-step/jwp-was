package testutils;

import http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HttpRequestGenerator {
    private HttpRequestGenerator() {}

    public static HttpRequest init(final String requestStr) throws IOException {
        return HttpRequest.readRawRequest(new ByteArrayInputStream(requestStr.getBytes()));
    }
}
