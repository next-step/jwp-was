package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {
    @ParameterizedTest(name = "[{index}] The major and minor numbers in {0} are {1} and {2}")
    @CsvSource(value = {"HTTP/1.1, 1, 1", "HTTP/2.4, 2, 4"})
    void parse(final String field, final String major, final String minor) {
        final HttpVersion version = HttpVersion.parse(field);
        assertThat(version.toString()).isEqualTo(field);
        assertThat(version.getMajor()).isEqualTo(Integer.parseInt(major));
        assertThat(version.getMinor()).isEqualTo(Integer.parseInt(minor));
    }

    @ParameterizedTest(name = "[{index}] {0} is a lower version than {1}")
    @CsvSource(value = {"HTTP/2.4, HTTP/2.13", "HTTP/2.4, HTTP/12.3"})
    void lowerThan(final String field1, final String field2) {
        final HttpVersion version1 = HttpVersion.parse(field1);
        final HttpVersion version2 = HttpVersion.parse(field2);
        assertThat(version1).isLessThan(version2);
        assertThat(version1.lowerThan(version2)).isTrue();
    }
}
