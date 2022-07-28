package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Protocol;

class StatusLineTest {

    @DisplayName("정상 응답 상태 코드")
    @Test
    void status_ok() {
        final StatusLine statusLine = new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.OK);

        assertThat(statusLine).isEqualTo(new StatusLine(Protocol.HTTP_1_1, StatusCode.OK));
    }

    @DisplayName("다른 페이지로 이동 상태 코드")
    @Test
    void status_found() {
        final StatusLine statusLine = new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.FOUND);

        assertThat(statusLine).isEqualTo(new StatusLine(Protocol.HTTP_1_1, StatusCode.FOUND));
    }

}
