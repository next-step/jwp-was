package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.domain.Protocol;
import webserver.domain.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProtocolTest {

    @DisplayName("값이 유효한 경우 프로토콜 객체를 정상적으로 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"http/1.0,http,1.0", "http/1.1,http,1.1", "http/2,http,2"})
    void createProtocol(String line, String protocol, String version) {

        Protocol createdProtocol = Protocol.newInstance(line);

        assertThat(createdProtocol).isEqualTo(new Protocol(protocol, Version.from(version)));
    }

    @DisplayName("값이 유효하지 않은 경우 프로토콜 객체를 생성하지 못한다.")
    @ParameterizedTest
    @ValueSource(strings = {"http/3", "http&1.0"})
    void createFailedProtocol(String invalidLine) {
        assertThatThrownBy(()-> Protocol.newInstance(invalidLine))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
