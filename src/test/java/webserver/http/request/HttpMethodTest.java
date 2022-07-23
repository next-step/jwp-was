package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class HttpMethodTest {
    @Test
    void GET_메소드_파싱() {
        HttpMethod actual = HttpMethod.from("GET");

        assertThat(actual).isEqualTo(HttpMethod.GET);
    }

    @Test
    void POST_메소드_파싱() {
        HttpMethod actual = HttpMethod.from("POST");

        assertThat(actual).isEqualTo(HttpMethod.POST);
    }

    @Test
    void 메소드_파싱_오류() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> HttpMethod.from("오류"))
                .withMessage("HTTP 메소드를 파싱할 수 없습니다.");
    }
}
