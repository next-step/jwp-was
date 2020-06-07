package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HttpMethod 테스트")
public class HttpMethodTest {

    @DisplayName("잘못된 HttpMethod 이름을 가지고, HttpMethod Enum으로 변환시 IllegalArgumentException 예외를 반환한다.")
    @Test
    void throwException() {
        assertThatThrownBy(() -> HttpMethod.findByName("Aet"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 HTTP method 이름이 존재하지 않습니다.");
    }

    @DisplayName("HttpMethod 이름을 HttpMethod Enum으로 변환한다.")
    @Test
    void convert() {
        HttpMethod httpMethod = HttpMethod.findByName("GET");
        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }
}
