package http;

import http.common.ProtocolAndVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolAndVersionTest {

    @Test
    @DisplayName("RequestLine의 프로토콜 버전으로 ProtocolAndVersion 객체를 생성한다")
    void test1() {
        final String input = "http/1.1";

        final ProtocolAndVersion result = new ProtocolAndVersion(input);

        assertThat(result).isEqualTo(new ProtocolAndVersion("http", "1.1"));
    }
}