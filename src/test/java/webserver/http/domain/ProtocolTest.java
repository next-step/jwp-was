package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.domain.exception.BadRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {

    @DisplayName("입력 메시지가 지원하지 않는 프로토콜인 경우 예외발생")
    @ParameterizedTest
    @ValueSource(strings = {"HTTP/1.1/3", "HTTP/1.0"})
    void parse_fail(String nonValidMessage) {
        assertThatThrownBy(() -> Protocol.from(nonValidMessage))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("지원하지 않는 프로토콜 방식입니다.");
    }

    @DisplayName("입력 메시지가 [타입]/[버전]과 같이 '/'가 정확히 1개를 포함한 경우, 타입, 버전을 값으로 갖는 Protocol 객체를 생성한다.")
    @Test
    void parse() {
        String validMessage = "HTTP/1.1";
        Protocol actual = Protocol.from(validMessage);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(Protocol.HTTP_1_1);
    }
}