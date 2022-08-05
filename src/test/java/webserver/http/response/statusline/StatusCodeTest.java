package webserver.http.response.statusline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusCodeTest {
    @Test
    @DisplayName("StatusCode 객체를 생성한다.")
    void create_StatusCode() {
        StatusCode statusCode = StatusCode.OK;
        assertThat(statusCode).isNotNull().isInstanceOf(StatusCode.class);
    }
}