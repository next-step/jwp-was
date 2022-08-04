package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequestHeader 테스트")
class HttpRequestHeaderTest {

    @DisplayName("HttpRequestHeader 생성 테스트")
    @Test
    void create() {
        HttpRequestHeader httpRequestHeader =
                HttpRequestHeader.of(
                        List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")
                );

        Map<String, String> result = httpRequestHeader.getHeaders();
        assertThat(result).contains(
                Map.entry("Host","www.nowhere123.com"),
                Map.entry("Accept","image/gif, image/jpeg, */*"),
                Map.entry("Accept-Language","en-us")
        );
    }

}
