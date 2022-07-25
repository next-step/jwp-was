package webserver.request;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void empty_strings_cannot_be_parsed(final String emptyString) {
        assertThatThrownBy(() -> Protocol.parse(emptyString))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효하지 않은 프로토콜 문자열은 파싱할 수 없다")
    @Test
    void invalid_protocol_and_version_string() {
        assertThatThrownBy(() -> Protocol.parse("protocol"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Protocol 생성")
    @Test
    void create_protocol() {
        final Protocol actual = Protocol.parse("HTTP/1.1");

        final Protocol expected = Protocol.of("HTTP", "1.1");

        assertThat(actual).isEqualTo(expected);
    }
}
