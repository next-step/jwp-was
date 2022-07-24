package webserver.http.request.requestline;

import org.junit.jupiter.api.Test;
import webserver.http.HttpProtocolSchema;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {

    @Test
    void create_GET_method() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /users HTTP/1.1");

        assertThat(httpRequestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequestLine.getHttpPath().getFullPath()).isEqualTo("/users");
        assertThat(httpRequestLine.getHttpProtocolSchema()).isEqualTo(new HttpProtocolSchema("HTTP/1.1"));
    }

    @Test
    void create_POST_method() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("POST /users HTTP/1.1");

        assertThat(httpRequestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequestLine.getHttpPath().getFullPath()).isEqualTo("/users");
        assertThat(httpRequestLine.getHttpProtocolSchema()).isEqualTo(new HttpProtocolSchema("HTTP/1.1"));
    }
}
