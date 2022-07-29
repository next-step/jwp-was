package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusCodeTest {

    @DisplayName("정상 응답 상태 코드")
    @Test
    void status_ok() {
        final String statusAndReason = StatusCode.OK.message();
        assertThat(statusAndReason).isEqualTo("200 OK");
    }

    @DisplayName("다른 페이지로 이동 상태 코드")
    @Test
    void status_found() {
        final String statusAndReason = StatusCode.FOUND.message();
        assertThat(statusAndReason).isEqualTo("302 Found");
    }

    @DisplayName("자원을 찾을 수 없는 상태 코드")
    @Test
    void status_not_found() {
        final String statusAndReason = StatusCode.NOT_FOUND.message();
        assertThat(statusAndReason).isEqualTo("404 Not Found");
    }
}
