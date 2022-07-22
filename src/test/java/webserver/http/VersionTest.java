package webserver.http;

import exception.InvalidRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VersionTest {

    @DisplayName("정상적인 Version이 들어왔을 때")
    @Test
    void getVersion() {
        assertThat(new Version("1.1")).isEqualTo(new Version("1.1"));
    }

    @DisplayName("잘못된 Version이 들어왔을 때")
    @Test
    void getVersion_WithWrongVersion() {
        Assertions.assertThatThrownBy(() -> new Version("1"))
                .isInstanceOf(InvalidRequestException.class);
    }

    @DisplayName("Version이 Null로 들어왔을 때")
    @Test
    void getVersion_WithNullVersion() {
        Assertions.assertThatThrownBy(() -> new Version(""))
                .isInstanceOf(InvalidRequestException.class);
    }
}