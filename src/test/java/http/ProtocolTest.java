package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("프로토콜 클래스")
public class ProtocolTest {

    @Test
    @DisplayName("프로토콜 문자열이 정상적이지 않으면 예외를 발생시킨다")
    void parsingFail() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Protocol("HTTP"));
    }

    @Test
    @DisplayName("프로토콜 문자열 파싱")
    void parse() {
        Protocol protocol = new Protocol("HTTP/1.1");

        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }
}
