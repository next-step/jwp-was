package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;

import static model.Constant.PROTOCOL_VERSION_ONE_ONE;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {

    @Test
    void tesetResponseLine_WithHttpStatusOk() {
        assertThat(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK))
                .isEqualTo(new ResponseLine("HTTP/1.1 OK"));
    }
}