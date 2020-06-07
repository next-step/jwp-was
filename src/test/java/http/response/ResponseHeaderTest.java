package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Response header를 관리하는 클래스")
class ResponseHeaderTest {
    private ResponseHeader responseHeader;

    @BeforeEach
    void initEnv() {
        responseHeader = ResponseHeader.init();
    }

    @Test
    @DisplayName("초기화")
    void init() {
        assertThatCode(ResponseHeader::init).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("location 쓰기")
    void location() {
        responseHeader.setLocation("some location");

        assertThat(responseHeader.getLocation()).isEqualTo("some location");
        assertThat(responseHeader.getHeaders()).hasSize(1);
    }

    @Test
    @DisplayName("location 쓰기를 하면 기존의 헤더들을 다 지워준다")
    void locationRemoveHeaders() {
        responseHeader.setHeader("key0", "value");
        responseHeader.setHeader("key1", "value");
        responseHeader.setHeader("key2", "value");
        responseHeader.setHeader("key3", "value");

        assertThat(responseHeader.getHeaders()).hasSize(4);
        responseHeader.setLocation("some location");
        assertThat(responseHeader.getHeaders()).hasSize(1);
    }
}
