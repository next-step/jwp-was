package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class CookiesTest {

    @DisplayName("정상적으로 Cookies가 생성되는지 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"a=b;c=d", "a= b; c = d"})
    void createCookiesTest(String line) {
        Cookies cookies = new Cookies(line);

        for (String cookie : line.split(";")) {
            String[] values = cookie.trim().split("=");
            assertThat(cookies.getCookie(values[0].trim())).isEqualTo(values[1].trim());
        }
    }

    @DisplayName("유효하지 않은 값으로 쿠키를 생성할때 적절한 예외를 발생시키는지 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"a", "a==", "a=a=a"})
    void createInvalidCookiesTest(String line) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Cookies(line);
        }).withMessage(Cookies.ILLEGAL_COOKIE);
    }
}
