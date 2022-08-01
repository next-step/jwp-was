package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import webserver.enums.Protocol;
import webserver.enums.HttpStatus;

class StatusLineTest {

    @Test
    void createMethodNotAllowedTest() {
        StatusLine statusLine = new StatusLine(Protocol.HTTP_1_1, HttpStatus.METHOD_NOT_ALLOWED);

        assertThat(statusLine.getProtocol()).isEqualTo(Protocol.HTTP_1_1);
        assertThat(statusLine.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void createOkTest() {
        StatusLine statusLine = new StatusLine(Protocol.HTTP_1_1, HttpStatus.OK);

        assertThat(statusLine.getProtocol()).isEqualTo(Protocol.HTTP_1_1);
        assertThat(statusLine.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void toStringTest() {
        StatusLine statusLine = new StatusLine(Protocol.HTTP_1_1, HttpStatus.OK);

        assertThat(statusLine.toString()).isEqualTo("HTTP/1.1 200 OK");
    }

}
