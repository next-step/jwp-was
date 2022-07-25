package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VersionTest {
    @Test
    @DisplayName("Version 객체를 생성한다.")
    void create_Version() {
        Version version = Version.ONE;
        assertThat(version).isNotNull().isInstanceOf(Version.class);
    }

    @Test
    @DisplayName("1.0/1.1/2.0 이외의 Version 은 예외가 발생한다.")
    void throw_exception_Version() {
        assertThatThrownBy(() -> Version.valueOfVersion("3.0")).isInstanceOf(IllegalArgumentException.class);
    }
}