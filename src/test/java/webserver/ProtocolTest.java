package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "HTTP/", "/1.1", "HTTP/ ", " /HTTP", " / ", "test"})
    @DisplayName("input 값이 null 또는 빈값인 경우 예외를 반환한다")
    public void invalid(String input) {
        assertThatThrownBy(() -> Protocol.from(input)).isInstanceOf(IllegalArgumentException.class);
    }
}
