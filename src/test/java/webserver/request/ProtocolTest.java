package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProtocolTest {

    @DisplayName("빈 문자열로 파싱하면 기본 프로토콜을 반환한다")
    @Test
    void parse_empty_protocol_then_default_protocol() {
        final Protocol actual = Protocol.parse("");

        assertThat(actual).isSameAs(Protocol.HTTP_1_1);
    }

    @DisplayName("프로토콜을 파싱한다")
    @ParameterizedTest
    @MethodSource
    void parse_protocol(String protocol, Protocol expected) {
        final Protocol actual = Protocol.parse(protocol);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> parse_protocol() {
        return Stream.of(
            Arguments.of("HTTP/1.1", Protocol.HTTP_1_1),
            Arguments.of("HTTP/2.0", Protocol.HTTP_2_0)
        );
    }

    @DisplayName("프로토콜 버전을 문자열로 반환한다")
    @Test
    void protocol_to_string() {
        final String actual = Protocol.HTTP_1_1.value();

        assertThat(actual).isEqualTo("HTTP/1.1");
    }
}
