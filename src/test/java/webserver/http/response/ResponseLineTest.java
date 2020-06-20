package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Protocol;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {

    @DisplayName("response line 생성")
    @Test
    void of() {

        // given
        HttpResponseStatus status = HttpResponseStatus.OK;

        // when
        ResponseLine responseLine = ResponseLine.of(status);

        // then
        assertThat(responseLine)
                .isEqualTo(new ResponseLine(Protocol.of("HTTP/1.1"), status));
    }

    @DisplayName("response line 메시지 생성")
    @Test
    void response() {

        // given
        HttpResponseStatus status = HttpResponseStatus.OK;
        ResponseLine responseLine = ResponseLine.of(status);

        // when
        String response = responseLine.response();

        // then
        assertThat(response)
                .isEqualTo("HTTP/1.1 200 OK\r\n");
    }
}