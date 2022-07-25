package webserver.request;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import enums.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestLineParseTest {
    @Test
    @DisplayName("GET 메소드 요청 파싱 테스트")
    void testGetRequest() {
        String request = "GET /users HTTP/1.1";

        RequestLine result = new RequestLine(request);

        assertThat(result.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(result.getPath()).isEqualTo("/users");
        assertThat(result.getProtocol()).isEqualTo("HTTP");
        assertThat(result.getVersion()).isEqualTo("1.1");
    }


    @Test
    @DisplayName("POST 메소드 요청 파싱 테스트")
    void testPostRequest() {
        String request = "POST /users HTTP/1.1";

        RequestLine result = new RequestLine(request);

        assertThat(result.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(result.getPath()).isEqualTo("/users");
        assertThat(result.getProtocol()).isEqualTo("HTTP");
        assertThat(result.getVersion()).isEqualTo("1.1");
    }


    @Test
    @DisplayName("Query String 요청 파싱 테스트")
    void testQueryStringRequest() {
        String request = "GET /users?name=name&password=password HTTP/1.1";

        RequestLine result = new RequestLine(request);

        assertThat(result.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(result.getPath()).isEqualTo("/users");
        assertThat(result.getQueryString().getParams()).isEqualTo(Map.of("name", "name", "password", "password"));
        assertThat(result.getProtocol()).isEqualTo("HTTP");
        assertThat(result.getVersion()).isEqualTo("1.1");
    }

    @Test()
    @DisplayName("잘못된 request 테스트")
    void testWrongRequest() {
        String request = "GET /users HTTP//1.2";

        assertThrows(IllegalArgumentException.class, () -> new RequestLine(request));
    }
}
