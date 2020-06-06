package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HeaderType 테스트")
class HeaderTypeTest {

    @DisplayName("잘못된 헤더 정보를 가지고 요청이 들어오면, IllegalArgumentException 예외를 반환한다.")
    @Test
    void wrongHeader() {
        assertThatThrownBy(() -> HeaderType.findByName("aa"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 헤더 정보입니다.");
    }

    @DisplayName("문자열 헤더 정보에 맞는, HeaderType enum을 반환한다.")
    @Test
    void name2() {
        HeaderType headerType = HeaderType.findByName("Content-Type");
        assertThat(headerType).isEqualTo(HeaderType.CONTENT_TYPE);
    }

}
