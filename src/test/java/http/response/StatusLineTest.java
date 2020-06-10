package http.response;

import http.common.HttpStatus;
import http.request.requestline.Protocol;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @ParameterizedTest
    @CsvSource({
        "'HTTP', '1.0'",
        "'HTTP', '1.1'",
    })
    void of(String protocol, String version) {
        for (HttpStatus httpStatus : HttpStatus.values()) {
            StatusLine statusLine = StatusLine.of(Protocol.of(getProtocolAndVersion(protocol, version)), httpStatus);

            assertThat(statusLine).isNotNull();
            assertThat(statusLine.toString()).isEqualTo(getProtocolAndVersion(protocol, version) + " " + httpStatus.getValue() + " " + httpStatus.getReasonPhrase());
        }
    }

    private String getProtocolAndVersion(String protocol, String version) {
        return protocol + "/" + version;
    }
}