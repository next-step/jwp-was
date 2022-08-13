package webserver.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusLineTest {

    @Test
    public void statusLineTest() {
        StatusLine statusLine = StatusLine.parse("HTTP/1.1 200 OK");

        Assertions.assertThat(statusLine.getVersion()).isEqualTo("HTTP/1.1");
        Assertions.assertThat(statusLine.getStatusCode()).isEqualTo("200");
        Assertions.assertThat(statusLine.getStatusReason()).isEqualTo("OK");
    }
}