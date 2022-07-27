package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestHeaderTest {

    private HttpRequestHeader requestHeader;

    @BeforeEach
    void setUp() {
        requestHeader = HttpRequestHeader.createEmpty();
    }

    @Test
    void createTest() {
        HttpRequestHeader httpHeaderTest = HttpRequestHeader.createEmpty();
        assertThat(httpHeaderTest).isNotNull();
    }

    @DisplayName("헤더에 key-value 로 정상 저장되어 확인된다.")
    @Test
    void headerMapTest() {
        requestHeader.putHeader("headerKey1", "headerValue1");
        requestHeader.putHeader("headerKey3", "headerValue3");

        assertThat(requestHeader.getHeader("headerKey1")).isEqualTo("headerValue1");
        assertThat(requestHeader.getHeader("headerKey3")).isEqualTo("headerValue3");
    }

    @DisplayName("헤더에 key 로 없는 것은 가져올 수 없다.")
    @Test
    void headerMapFailTest() {
        requestHeader.putHeader("headerKey1", "headerKey1");

        assertThatThrownBy(
            () -> requestHeader.getHeader("invalidKey")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
