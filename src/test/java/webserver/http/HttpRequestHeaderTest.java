package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpRequestHeader 테스트")
class HttpRequestHeaderTest {

    @DisplayName("HttpRequestHeader 생성 테스트")
    @Test
    void create() {
        HttpRequestHeader httpRequestHeader =
                HttpRequestHeader.of(
                        List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")
                );

        Map<HttpHeaders, String> result = httpRequestHeader.getHeaders();
        assertThat(result).contains(
                Map.entry(HttpHeaders.HOST, "www.nowhere123.com"),
                Map.entry(HttpHeaders.ACCEPT, "image/gif, image/jpeg, */*"),
                Map.entry(HttpHeaders.ACCEPT_LANGUAGE, "en-us")
        );
    }

}
