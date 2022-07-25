package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestTest {

    @DisplayName("클라이언트로부터 받은 요청을 파싱하여 객체에 저장한다.")
    @Test
    void create() {
        ByteArrayInputStream in = new ByteArrayInputStream(httpRequest().getBytes(StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(in);

        assertAll(
            () -> assertThat(request.getMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(request.getRequestURI()).isEqualTo("/users"),
            () -> assertThat(request.getHeader("Accept")).isEqualTo("text/html"),
            () -> assertThat(request.getHeader("Connection")).isEqualTo("keep-alive"),
            () -> assertThat(request.getParameter("userId")).isEqualTo("javajigi")
        );
    }

    private String httpRequest() {
        String lineSeparator = System.lineSeparator();
        StringBuilder builder = new StringBuilder("GET /users?userId=javajigi HTTP/1.1 " + lineSeparator);
        builder.append("Accept: text/html").append(lineSeparator);
        builder.append("Connection: keep-alive").append(lineSeparator);
        return builder.toString();
    }
}
