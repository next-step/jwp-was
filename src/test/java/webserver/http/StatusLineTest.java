package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @DisplayName("HTTP 응답 status line 생성 시, 기본 상태 값을 200 OK로 생성한다.")
    @Test
    void create() {
        StatusLine statusLine = new StatusLine();
        assertThat(statusLine.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.toString()).hasToString("HTTP/1.1 200 OK");
    }

    @DisplayName("HTTP 응답 status line의 응답 상태 코드를 변경할 수 있다.")
    @Test
    void setStatus() {
        StatusLine statusLine = new StatusLine();
        statusLine.setStatus(HttpStatus.FOUND);
        assertThat(statusLine.toString()).hasToString("HTTP/1.1 302 Found");
    }
}
