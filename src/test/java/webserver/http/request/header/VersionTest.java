package webserver.http.request.header;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import webserver.http.request.header.exception.InvalidVersionException;
import webserver.http.request.header.Version;

class VersionTest {

    @DisplayName("버전은 x.x 형식이어야 한다.")
    @Test
    void createFailedByVersionType() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Version("1.1.0"))
                .isInstanceOf(InvalidVersionException.class)
                .hasMessage("유효하지 않은 버전 입니다. x.x 형식으로 버전을 명시하세요.");
    }

    @DisplayName("버전은 숫자만 들어가야 한다.")
    @ParameterizedTest
    @CsvSource({"1.x", "x.1", "1.1-snapshot", "a.b"})
    void createFailedByNotDigit(String version) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Version(version))
                .isInstanceOf(InvalidVersionException.class)
                .hasMessage("유효하지 않은 버전 입니다. 버전은 숫자로 구성되어야 합니다.");
    }
}
