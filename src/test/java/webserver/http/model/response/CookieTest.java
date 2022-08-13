package webserver.http.model.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CookieTest {

    @DisplayName("Cookie 의 name에 빈 값을 입력하게 되면 예외가 발생합니다.")
    @Test
    void construct_exception() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Cookie(" "))
                .withMessageContaining("Cookie Name은 빈 값을 입력할 수 없습니다.");
    }
}