package webserver.http.response;

import org.junit.jupiter.api.Test;

class HttpResponseHeadersTest {
    @Test
    void name() {

        HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();

        httpResponseHeaders.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponseHeaders.addHeader("Content-Length", "2");
    }
}
