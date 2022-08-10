package webserver.http.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class HttpMethodTest {

    @DisplayName("RequestLine의 메소드 문자열을 입력하면 해당 Enum 값이 출력된다. ")
    @Test
    void construct() {
        HttpMethod httpMethod = HttpMethod.of("GET");
        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("RequestLine의 Method 생성시 Method enum에 설정된 값이 아니면 예외를 발생시킨다.")
    @Test
    void construct_unExistEnum() {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpMethod.of("UnExistMethod"));
    }

    @DisplayName("POST Method 인지 확인")
    @Test
    void isPost() {
        assertThat(HttpMethod.isPost("post")).isTrue();
        assertThat(HttpMethod.isPost("get")).isFalse();
    }
}
