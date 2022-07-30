package webserver.http.protocol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProtocolTest {

    @DisplayName("HTTP/1.1 타입의 프로토콜 객체 정적 생성메서드")
    @Test
    void http1Point1() {
        Protocol actual = Protocol.http1Point1();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Protocol("HTTP", "1.1"));
    }
}