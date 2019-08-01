package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {
    @ParameterizedTest
    @CsvSource(value = {"HTTP/1.1, 1, 1", "HTTP/2.4, 2, 4"})
    void parse(final String field, final String major, final String minor) {
        final HttpVersion httpVersion = HttpVersion.parse(field);
        assertThat(httpVersion.getMajor()).isEqualTo(Integer.parseInt(major));
        assertThat(httpVersion.getMinor()).isEqualTo(Integer.parseInt(minor));
    }
}
