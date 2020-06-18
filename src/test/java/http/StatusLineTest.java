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
        String reasonPhrase = "OK";

        String rawStatusLine = protocol + "/" + version + " " + statusCode + " " + reasonPhrase + "\r\n";

        // when
        StatusLine statusLine = sut.from(rawStatusLine);

        // then
        assertThat(statusLine.makeStatusLineString()).isEqualTo(rawStatusLine);
        assertThat(statusLine.getHttpProtocol().getProtocol()).isEqualTo(protocol);
        assertThat(statusLine.getHttpProtocol().getVersion()).isEqualTo(version);
        assertThat(statusLine.getStatusCode()).isEqualTo(statusCode);
        assertThat(statusLine.getReasonPhrase()).isEqualTo(reasonPhrase);
    }

    @Test
    void new_statusLine_isEmpty() {
        // given
        String statusLineString = "";

        // when
        StatusLine statusLine = sut.from(statusLineString);

        // then
        assertThat(statusLine.getStatusCode()).isEqualTo(Integer.toString(HttpStatus.BAD_REQUEST.getValue()));
        assertThat(statusLine.getReasonPhrase()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}