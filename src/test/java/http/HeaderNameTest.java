package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HeaderName 테스트")
class HeaderNameTest {

    @DisplayName("잘못된 헤더 정보를 가지고 요청이 들어오면, IllegalArgumentException 예외를 반환한다.")
    @Test
    void wrongHeader() {
        assertThatThrownBy(() -> HeaderName.findByName("aa"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 헤더 정보입니다.");
    }

    @DisplayName("문자열 헤더 정보에 맞는, HeaderType enum을 반환한다.")
    @Test
    void name2() {
        HeaderName headerName = HeaderName.findByName("Content-Type");
        assertThat(headerName).isEqualTo(HeaderName.CONTENT_TYPE);
    }

}
