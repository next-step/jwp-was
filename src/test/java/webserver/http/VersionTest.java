package webserver.http;

import exception.IllegalProtocolVersionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Protocol Version 테스트")
class VersionTest {

    @Test
    @DisplayName("올바른 Protocol Version일 경우 생성한다.")
    void create() {
        // Arrange
        String inputVersion = "1.1";

        // Act
        Version actual = Version.from(inputVersion);

        // Assert
        assertThat(actual).isEqualTo(Version.VERSION1_1);
    }

    @Test
    @DisplayName("올바르지 않은 Protocol Version이 입력될 경우 IllegalProtocolVersionException이 발생한다.")
    void invalidVersion() {
        // Arrange
        String inputVersion = "0.9";

        // Act & Assert
        assertThatThrownBy(() -> Version.from(inputVersion))
                .isInstanceOf(IllegalProtocolVersionException.class);
    }
}