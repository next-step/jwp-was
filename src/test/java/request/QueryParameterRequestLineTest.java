package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryParameterRequestLineTest {
    @DisplayName("QueryParameters parsing 성공")
    @Test
    void createQueryParametersRequestLine_success() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertAll(
            () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET.name()),
            () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
            () -> assertThat(requestLine.getPathValueOf("userId")).isEqualTo("javajigi"),
            () -> assertThat(requestLine.getPathValueOf("password")).isEqualTo("password"),
            () -> assertThat(requestLine.getPathValueOf("name")).isEqualTo("JaeSung"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }
}
