package webserver;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProtocolTest {

    @DisplayName("빈 문자열은 파싱할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_strings_cannot_be_parsed(String emptyString) {
        assertThatThrownBy(() -> Protocol.parse(emptyString))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("프로토콜과 버전 정상 파싱")
    @Test
    void parse() {
        final Protocol protocol = Protocol.parse("HTTP/1.1");

        assertAll(
            () -> assertThat(protocol.getType()).isEqualTo("HTTP"),
            () -> assertThat(protocol.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("유효하지 않은 프로토콜 문자열")
    @Test
    void invalid_protocol_and_version_string() {
        assertThatThrownBy(() -> Protocol.parse("protocol"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
