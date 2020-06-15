package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {
    private StatusLine sut;

    @Test
    void new_statusLine() {
        // given
        String protocol = "HTTP";
        String version = "1.1";
        String statusCode = "200";
        String reason = "OK";

        String rawStatusLine = protocol + "/" + version + " " + statusCode + " " + reason + "\r\n";

        // when
        StatusLine statusLine = sut.from(rawStatusLine);

        // then
        assertThat(statusLine.makeStatusLineString()).isEqualTo(rawStatusLine);
        assertThat(statusLine.getHttpProtocol().getProtocol()).isEqualTo(protocol);
        assertThat(statusLine.getHttpProtocol().getVersion()).isEqualTo(version);
        assertThat(statusLine.getStatusCode()).isEqualTo(statusCode);
        assertThat(statusLine.getReason()).isEqualTo(reason);
    }

    @Test
    void new_statusLine_isEmpty() {
        // given
        String statusLine = "";

        // when
        StatusLine sta = sut.from(statusLine);

        // then
        assertThat(sta).extracting("class").isEqualTo(StatusLine.EmptyStatusLine.class);
    }

}