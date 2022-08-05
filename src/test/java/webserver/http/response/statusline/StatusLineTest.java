package webserver.http.response.statusline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class StatusLineTest {
    @Test
    @DisplayName("StatusLine 객체를 생성한다.")
    void create_StatusLine() {
        StatusLine statusLine = new StatusLine(new Protocol("HTTP", Version.ONE_ONE), StatusCode.OK);
        assertThat(statusLine).isNotNull().isInstanceOf(StatusLine.class);
    }

    @Test
    @DisplayName("응답 프로토콜, 응답 코드가 null 일 경우 예외가 발생한다.")
    void throw_exception_response_null() {
        assertAll(
                () -> assertThatThrownBy(() -> new StatusLine(null, StatusCode.OK)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new StatusLine(new Protocol("HTTP", Version.ONE_ONE), null)).isInstanceOf(IllegalArgumentException.class)
        );
    }
}