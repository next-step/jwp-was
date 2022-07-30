package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("응답 쿠키")
class ResponseCookieTest {

    @Test
    @DisplayName("키와 값으로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> ResponseCookie.of("key", "value"));
    }

    @Test
    @DisplayName("키와 값은 필수")
    void instance_nullOrEmpty_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() -> ResponseCookie.of("", "value")),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> ResponseCookie.of("key", ""))
        );
    }
}
