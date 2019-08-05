package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @DisplayName("StatusLine 출력 확인")
    @Test
    void StatusLine_출력_확인() {
        // given
        final StatusLine statusLine = new StatusLine(HttpVersion.HTTP_1_1, HttpStatus.OK);

        // when
        final String actual = statusLine.toString();

        // then
        assertThat(actual).isEqualTo("HTTP/1.1 200 OK \r\n");
    }
}