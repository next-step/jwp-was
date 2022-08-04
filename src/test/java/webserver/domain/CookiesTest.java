package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CookiesTest {

    private Cookies cookies;

    @Test
    void createTest() {
        this.cookies = Cookies.of("yummy_cookie=choco; tasty_cookie=strawberry");

        assertThat(cookies.size()).isEqualTo(2);
    }

    @DisplayName("빈문자열로 쿠키 만들면 비어있다.")
    @Test
    void createTest2() {
        this.cookies = Cookies.of("");

        assertThat(cookies.size()).isEqualTo(0);
    }

    @DisplayName("JSESSIONID 정상로드한다.")
    @Test
    void getSessionIdTest1() {
        this.cookies = Cookies.of("yummy_cookie=choco; JSESSIONID=testSessionId; tasty_cookie=strawberry");

        assertThat(this.cookies.getSessionId()).isEqualTo("testSessionId");
    }

    @DisplayName("JSESSIONID 없으면 빈문자열 가져온다.")
    @Test
    void getSessionIdTest2() {
        this.cookies = Cookies.of("yummy_cookie=choco; this=sparta; tasty_cookie=strawberry");

        assertThat(this.cookies.getSessionId()).isEmpty();
    }

}
