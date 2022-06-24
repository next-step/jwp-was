package webserver.domain.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.domain.http.Protocol;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "HTTP/", "/1.1", "HTTP/ ", " /HTTP", " / ", "test"})
    @DisplayName("예외를 반환 검증")
    public void invalid(String input) {
        assertThatThrownBy(() -> Protocol.from(input)).isInstanceOf(IllegalArgumentException.class);
    }
}
