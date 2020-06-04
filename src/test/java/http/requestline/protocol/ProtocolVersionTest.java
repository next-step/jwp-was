package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtocolVersionTest {

    @DisplayName("올바른 프로토콜 버전으로 생성하기")
    @Test
    void create() {
        /* given */
        String version1 = "1.0";
        String version2 = "1.1";

        /* when */
        ProtocolVersion protocolVersion1 = new ProtocolVersion(version1);
        ProtocolVersion protocolVersion2 = new ProtocolVersion(version2);

        /* then */
        assertThat(protocolVersion1).isEqualTo(new ProtocolVersion(version1));
        assertThat(protocolVersion2).isEqualTo(new ProtocolVersion(version2));

        assertThat(protocolVersion1.getVersion()).isEqualTo(version1);
        assertThat(protocolVersion2.getVersion()).isEqualTo(version2);
    }

    @DisplayName("프로토콜 버전은 [한자리숫자][.][한자리숫자] 가 스펙이다")
    @ParameterizedTest
    @ValueSource(strings = {"1,1", "1", "1.", "1.11"})
    void regex(String version) { /* given */
        /* when */ /* then */
        assertThrows(IllegalRequestLineParsingException.class, () -> new ProtocolVersion(version));
    }
}