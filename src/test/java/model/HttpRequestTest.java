package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpRequest 테스트")
class HttpRequestTest {

    @DisplayName("HttpRequest 생성")
    @Test
    void create() {
        HttpRequest request = HttpRequest.of(
                List.of("GET /docs/index.html HTTP/1.1", "Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*")
        );
        assertThat(request.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/docs/index.html");
        assertThat(request.getHeader()).contains(
                Map.entry("Host","www.nowhere123.com"),
                Map.entry("Accept","image/gif, image/jpeg, */*")

        );

    }

}
