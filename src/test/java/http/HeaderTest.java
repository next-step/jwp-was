package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Header 테스트")
public class HeaderTest {

    @DisplayName("헤더가 '이름과: 값' 형태가 아닌 경우, IllegalArgumentException 예외를 반환한다.")
    @Test
    void wrongFormat() {
        assertThatThrownBy(() -> new Header("Host;"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("헤더는 이름과 값이 콜론을 가지고 이루어져야만 합니다.");

        assertThatThrownBy(() -> new Header("Host; localhost:8080"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("헤더는 이름과 값이 콜론을 가지고 이루어져야만 합니다.");
    }

    @DisplayName("문자열로된 '이름과: 값' 형태의 헤더로, Header 객체를 생성한다.")
    @Test
    void create() {
        Header header = new Header("Host: localhost:8080");

        assertThat(header.getName()).isEqualTo("Host");
        assertThat(header.getValue()).isEqualTo("localhost:8080");
    }
}
