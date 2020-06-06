package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HTTPProtocol 테스트")
public class HTTPProtocolTest {

    @DisplayName("잘못된 문자열을 사용한 경우, IllegalArgumentException예외를 반환한다.")
    @Test
    void throwException1() {
        assertThatThrownBy(() -> new HTTPProtocol("aaa"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로토콜 정보를 다시 한번 확인 부탁드립니다.");
    }

    @DisplayName("프로토콜과 프로토콜 버전 사이의 구분자를 잘못 사용한 경우, IllegalArgumentException예외를 반환한다.")
    @Test
    void throwException2() {
        assertThatThrownBy(() -> new HTTPProtocol("HTTP@1.1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("프로토콜 정보를 다시 한번 확인 부탁드립니다.");
    }

    @DisplayName("잘못된 프로토콜을 사용한 경우, IllegalArgumentException예외를 반환한다.")
    @Test
    void throwException3() {
        assertThatThrownBy(() -> new HTTPProtocol("HOT/1.1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 프로토콜을 사용하셨습니다.");
    }

    @DisplayName("잘못된 프로토콜 버전을 사용한 경우, IllegalArgumentException예외를 반환한다.")
    @Test
    void throwException4() {
        assertThatThrownBy(() -> new HTTPProtocol("HTTP/1.a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 프로토콜 버전을 사용하셨습니다.");
    }

    @DisplayName("문자열로된 HTTP 프로토콜 정보를 가지고, 프로토콜 객체를 생성한다.")
    @Test
    void create() {
        HTTPProtocol HTTPProtocol = new HTTPProtocol("HTTP/1.1");

        assertThat(HTTPProtocol.getProtocol()).isEqualTo("HTTP");
        assertThat(HTTPProtocol.getVersion()).isEqualTo(1.1);
    }
}
