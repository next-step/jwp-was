package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("프로토콜")
class ProtocolTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> Protocol.from("HTTP/1.1")),
                () -> assertThatNoException().isThrownBy(() -> Protocol.of("HTTP", "1.1"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "HTTP", "1.0"})
    @DisplayName("문자열, 버전은 필수")
    void instance_emptyString_thrownIllegalArgumentException(String protocol) {
        assertThatIllegalArgumentException().isThrownBy(() -> Protocol.from(protocol));
    }

    @Test
    @DisplayName("하나의 문자열로 제공하면 이름과 버전을 구분")
    void instance_string_parseProtocol() {
        //given
        Protocol http11 = Protocol.from("HTTP/1.1");
        //when, then
        assertThat(http11).isEqualTo(Protocol.of("HTTP", "1.1"));
    }
}
