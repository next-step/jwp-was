package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeaderTest {

    private HttpHeader httpHeader;

    @BeforeEach
    void setUp() {
        httpHeader = new HttpHeader();
    }

    @Test
    void createTest() {
        HttpHeader httpHeaderTest = new HttpHeader();
        assertThat(httpHeaderTest).isNotNull();
    }

    @DisplayName("헤더에 key-value 로 정상 저장되어 확인된다.")
    @Test
    void headerMapTest() {
        httpHeader.putHeader("headerKey1", "headerValue1");
        httpHeader.putHeader("headerKey3", "headerValue3");

        assertThat(httpHeader.getHeader("headerKey1")).isEqualTo("headerValue1");
        assertThat(httpHeader.getHeader("headerKey3")).isEqualTo("headerValue3");
    }

    @DisplayName("헤더에 key 로 없는 것은 가져올 수 없다.")
    @Test
    void headerMapFailTest() {
        httpHeader.putHeader("headerKey1", "headerKey1");

        assertThatThrownBy(
            () -> httpHeader.getHeader("invalidKey")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
