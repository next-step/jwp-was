package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpRequestTest {

    @Test
    void session() {
        RequestLine requestLine1 = new RequestLine.Builder(HttpMethod.GET, "/test", "HTTP/1.1").build();
        RequestHeader requestHeader1 = RequestHeader.from(Collections.emptyList());
        RequestBody requestBody1 = new RequestBody(new HttpParameter(Collections.emptyMap()));
        HttpSessionManager httpSessionManager = new HttpSessionManager();
        HttpRequest httpRequest1 = new HttpRequest(requestLine1, requestHeader1, requestBody1, httpSessionManager);

        assertNotNull(httpRequest1.getSessionId());
        httpRequest1.getSession().setAttribute("id", "jun");
        assertThat(httpRequest1.getSession().getAttribute("id")).isEqualTo("jun");

        RequestLine requestLine2 = new RequestLine.Builder(HttpMethod.GET, "/test", "HTTP/1.1").build();
        RequestHeader requestHeader2 = RequestHeader.from(Arrays.asList("Cookie: session-key=" + httpRequest1.getSessionId()
                + ";"));
        RequestBody requestBody2 = new RequestBody(new HttpParameter(Collections.emptyMap()));

        HttpRequest httpRequest2 = new HttpRequest(requestLine2, requestHeader2, requestBody2, httpSessionManager);
        assertThat(httpRequest2.getSession().getAttribute("id")).isEqualTo("jun");
    }

}
