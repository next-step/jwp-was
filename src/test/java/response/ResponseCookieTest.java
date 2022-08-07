package response;

import static org.assertj.core.api.Assertions.assertThat;

import constant.HttpCookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResponseCookieTest {

    @Test
    @DisplayName("JESSIONID가 전체 경로로 반환하는지 값을 확인합니다.")
    void makeJessionIdTest() {
        ResponseCookie responseCookie = ResponseCookie.of(HttpCookie.JSESSIONID.getValue(), "1234");

        assertThat(responseCookie.toString()).isEqualTo("JSESSIONID=1234; path=/");
    }

}
