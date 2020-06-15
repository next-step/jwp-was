package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {

    @DisplayName("response line 생성")
    @Test
    void of() {

        // given
        String code = "200";
        String statusCodeMessage = "OK";

        // when
        ResponseLine responseLine = ResponseLine.of(code, statusCodeMessage);

        // then
        assertThat(responseLine)
                .isEqualTo(new ResponseLine(Protocol.of("HTTP/1.1"), code, statusCodeMessage));
    }

    @DisplayName("response line 메시지 생성")
    @Test
    void response() {

        // given
        String code = "200";
        String statusCodeMessage = "OK";
        ResponseLine responseLine = ResponseLine.of(code, statusCodeMessage);

        // when
        String response = responseLine.response();

        // then
        assertThat(response)
                .isEqualTo("HTTP/1.1 200 OK\r\n");
    }
}