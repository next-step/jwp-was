package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("응답 헤드")
class ResponseHeaderTest {

    @Test
    @DisplayName("문자열 리스트나 맵으로 생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> ResponseHeader.from(null)),
                () -> assertThatNoException().isThrownBy(() -> ResponseHeader.from(Map.of("Connection", "keep-alive"))),
                () -> assertThatNoException().isThrownBy(ResponseHeader::empty)
        );
    }

    @Test
    @DisplayName("null로 생성하면 빈 상태로 생성")
    void instance_null_emtpyHeader() {
        assertThat(ResponseHeader.from(null))
                .isEqualTo(ResponseHeader.empty());
    }

    @Test
    @DisplayName("값 추가")
    void add() {
        //given
        ResponseHeader emptyHeader = ResponseHeader.empty();
        //when, then
        assertThat(emptyHeader.add("key", "value"))
                .isEqualTo(ResponseHeader.from(Map.of("key", "value")));
    }

    @Test
    @DisplayName("개체들 조회")
    void entries() {
        assertThat(ResponseHeader.from(Collections.singletonMap("key", "value")).entries())
                .containsExactly(new AbstractMap.SimpleEntry("key", "value"));
    }
}
