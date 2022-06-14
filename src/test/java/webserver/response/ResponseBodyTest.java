package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {

    @DisplayName("ResponseBody 를 bytes 로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        assertThat(new String(new ResponseBody("Hello World".getBytes()).toBytes()))
                .isEqualTo("Hello World");
    }

    @DisplayName("ResponseBody 의 Content-Length 를 알 수 있어야 한다.")
    @Test
    void getLength() {
        assertThat(new ResponseBody("Hello World".getBytes()).getContentLength())
                .isEqualTo(11);
    }
}
