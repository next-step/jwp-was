package webserver.http.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Protocol;
import webserver.http.view.ProtocolParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolParserTest {
    private final ProtocolParser protocolParser = new ProtocolParser();

    @DisplayName("입력 메시지가 [타입]/[버전]/[기타]와 같이 '/'가 정확히 1개가 포함되지 않은 경우 예외발생")
    @Test
    void parse_fail() {
        String nonValidMessage = "HTTP/1.1/3";
        assertThatThrownBy(() -> protocolParser.parse(nonValidMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'[타입]/[버전]' 형식의 HTTP 프로토콜 메시지가 아닙니다. {message=HTTP/1.1/3}");
    }

    @DisplayName("입력 메시지가 [타입]/[버전]과 같이 '/'가 정확히 1개를 포함한 경우, 타입, 버전을 값으로 갖는 Protocol 객체를 생성한다.")
    @Test
    void parse() {
        String validMessage = "HTTP/1.1";
        Protocol actual = protocolParser.parse(validMessage);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Protocol("HTTP", "1.1"));
    }
}