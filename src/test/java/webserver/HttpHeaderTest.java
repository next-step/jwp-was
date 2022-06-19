package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {

    @DisplayName("HttpHeader 객체를 생성할 수 있다")
    @Test
    public void create() {
        List<String> requestHeader = List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"
        );

        HttpHeader actual = HttpHeader.from(requestHeader);

        assertThat(actual.get("Host")).isEqualTo("localhost:8080");
        assertThat(actual.get("Connection")).isEqualTo("keep-alive");
        assertThat(actual.get("Content-Length")).isEqualTo("59");
        assertThat(actual.get("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(actual.get("Accept")).isEqualTo("*/*");
    }


    @DisplayName("key 값 조회 시 결과가 없으면 예외를 반환한다")
    @Test
    public void invalidGet() {
        List<String> requestHeader = List.of("Host: localhost:8080");

        HttpHeader actual = HttpHeader.from(requestHeader);

        assertThatThrownBy(() -> actual.get("connection")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로그인 성공 여부를 확인할 수 있다")
    @Test
    public void isLogined() {
        List<String> requestHeader = List.of("Cookie: logined=true");

        HttpHeader actual = HttpHeader.from(requestHeader);

        assertThat(actual.isSetCookie()).isTrue();
    }
}