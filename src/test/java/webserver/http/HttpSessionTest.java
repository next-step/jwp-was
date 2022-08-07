package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpSession 단위 테스트")
public class HttpSessionTest {
    @DisplayName("고유한 세션 아이디를 반환한다.")
    @Test
    void getId() {
        // when
        HttpSession httpSession1 = new HttpSession();
        HttpSession httpSession2 = new HttpSession();

        // then
        assertAll(
                () -> assertThat(httpSession1.getId()).isNotBlank(),
                () -> assertThat(httpSession2.getId()).isNotBlank(),
                () -> assertThat(httpSession1.getId()).isNotEqualTo(httpSession2.getId())
        );
    }
}
