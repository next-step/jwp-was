package http.request.requestline.protocol;

import http.request.requestline.exception.RequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolVersionTest {

    @DisplayName("올바른 프로토콜 버전으로 생성하기")
    @ParameterizedTest
    @ValueSource(strings = {"1.0", "1.1"})
    void create(String version) { /* given */
        /* when */
        ProtocolVersion protocolVersion = new ProtocolVersion(version);

        /* then */
        assertThat(protocolVersion).isEqualTo(new ProtocolVersion(version));
        assertThat(protocolVersion.getVersion()).isEqualTo(version);
    }

    @DisplayName("프로토콜 버전은 [한자리숫자][.][한자리숫자] 가 스펙이다")
    @ParameterizedTest
    @ValueSource(strings = {"1,1", "1", "1.", "1.11"})
    void regex(String version) { /* given */
        /* when */ /* then */
        assertThrows(RequestLineParsingException.class, () -> new ProtocolVersion(version));
    }
}
