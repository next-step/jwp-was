package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import webserver.enums.Protocol;
import webserver.enums.StatusCode;

class StatusLineTest {

    @Test
    void createTest() {
        StatusLine statusLine = new StatusLine(Protocol.HTTP_1_1, StatusCode.OK);

        assertThat(statusLine.getProtocol()).isEqualTo(Protocol.HTTP_1_1);
        assertThat(statusLine.getStatusCode()).isEqualTo(StatusCode.OK);
    }

    @Test
    void toStringTest() {
        StatusLine statusLine = new StatusLine(Protocol.HTTP_1_1, StatusCode.OK);

        assertThat(statusLine.toString()).isEqualTo("HTTP/1.1 200 OK");
    }

}
