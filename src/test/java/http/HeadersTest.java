package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Headers 테스트")
public class HeadersTest {

    @DisplayName("header문자열을 가지고 Headers 객체를 생성한다.")
    @Test
    void create() {
        Headers headers = new Headers();
        headers.add("Host: localhost:8080");

        assertThat(headers.getHeaders()).hasSize(1);
        assertThat(headers.getHeaders()).contains(new Header("Host: localhost:8080"));
    }

    @DisplayName("원하는 Header 객체를 조회한다.")
    @Test
    void create2() {
        Headers headers = new Headers();
        headers.add("Host: localhost:8080");

        assertThat(headers.getHeader(HeaderName.HOST)).isEqualTo(Optional.of(new Header("Host: localhost:8080")));
    }
}
