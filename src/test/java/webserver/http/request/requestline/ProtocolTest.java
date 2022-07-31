package webserver.http.request.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {
    @Test
    @DisplayName("Protocol 객체를 생성한다.")
    void create_Protocol() {
        Protocol protocol = new Protocol("HTTP", Version.ONE_ONE);
        assertThat(protocol).isNotNull().isInstanceOf(Protocol.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("protocol 요청 값이 null 또는 비어있을 경우 예외가 발생한다.")
    void throw_exception_protocol_null_or_empty(String protocolString) {
        assertThatThrownBy(() -> Protocol.parse(protocolString)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP protocol 과 version 을 '/'으로 구분한 데이터 갯수가 2가 아닐 경우 예외가 발생한다.")
    void throw_exception_protocol_and_version_parse_element_number_not_2() {
        assertThatThrownBy(() -> Protocol.parse("HTTP/1.1/test")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HTTP 요청 protocol 이 'HTTP' 가 아닐 경우 예외가 발생한다.")
    void throw_exception_protocol_not_HTTP() {
        assertThatThrownBy(() -> Protocol.parse("HTTPS/1.1")).isInstanceOf(IllegalArgumentException.class);
    }
}