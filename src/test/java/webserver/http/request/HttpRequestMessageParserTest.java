package webserver.http.request;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import webserver.http.request.requestline.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestMessageParserTest {

    @Test
    void name() {
        HttpRequestMessage httpRequestMessage = HttpRequestMessageParser.parse(ImmutableList.of(
                "GET /index.html?a=2 HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        ));

        assertThat(httpRequestMessage.httpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequestMessage.httpPath()).isEqualTo("/index.html");
        assertThat(httpRequestMessage.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestMessage.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequestMessage.getHeader("Accept")).isEqualTo("*/*");
    }
}
