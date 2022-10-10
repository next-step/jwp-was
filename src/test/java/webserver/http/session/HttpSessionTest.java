package webserver.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HttpSession 테스트")
class HttpSessionTest {

    @Test
    @DisplayName("HttpSession 객체를 생성한다.")
    void create() {
        HttpSession actual = new HttpSession();

        assertThat(actual.getId()).isNotNull();
    }
}
