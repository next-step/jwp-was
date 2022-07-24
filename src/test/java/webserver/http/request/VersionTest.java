package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "1.1", "2"})
    void 버전_파싱(String expected) {
        Version version = new Version(expected);

        String actual = version.value();

        assertThat(actual).isEqualTo(expected);
    }
}
