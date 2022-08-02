package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseHeaderTest {

    private HttpResponseHeader responseHeader;

    @BeforeEach
    void setUp() {
        responseHeader = HttpResponseHeader.createEmpty();
    }

    @Test
    void createTest() {
        HttpResponseHeader httpHeaderTest = HttpResponseHeader.createEmpty();
        assertThat(httpHeaderTest).isNotNull();
    }

    @DisplayName("헤더에 key-value 로 정상 저장되어 확인된다.")
    @Test
    void headerMapTest() {
        responseHeader.putHeader("headerKey1", "headerValue1");
        responseHeader.putHeader("headerKey3", "headerValue3");

        assertThat(responseHeader.getHeader("headerKey1")).isEqualTo("headerValue1");
        assertThat(responseHeader.getHeader("headerKey3")).isEqualTo("headerValue3");
    }

    @DisplayName("헤더에 key 로 없는 것은 가져올 수 없다.")
    @Test
    void headerMapFailTest() {
        responseHeader.putHeader("headerKey1", "headerKey1");

        assertThatThrownBy(
            () -> responseHeader.getHeader("invalidKey")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
