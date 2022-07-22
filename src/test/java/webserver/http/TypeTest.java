package webserver.http;

import exception.IllegalProtocolTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Protocol Type 테스트")
class TypeTest {

    @Test
    @DisplayName("올바른 HTTP Protocol Type 생성")
    void create() {
        // Arrange
        final String inputType = "http";

        // Act
        final Type actual = Type.from(inputType);

        // Assert
        assertThat(actual).isEqualTo(Type.HTTP);
    }

    @Test
    @DisplayName("올바르지 않은 Protocol Type이 입력될 경우 IllegalProtocolTypeException이 발생한다.")
    void invalidProtocolType() {
        // Arrange
        final String inputType = "SMTP";

        // Act & Assert
        assertThatThrownBy(() -> Type.from(inputType))
                .isInstanceOf(IllegalProtocolTypeException.class);
    }
}