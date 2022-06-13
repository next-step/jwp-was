package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {

    @DisplayName("ResponseBody 를 String 으로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        assertThat(new ResponseBody("Hello World").toString())
                .isEqualTo("Hello World");
    }

    @DisplayName("ResponseBody 를 Byte 길이를 알 수 있어야 한다.")
    @Test
    void getLength() {
        assertThat(new ResponseBody("Hello World").getBytesLength())
                .isEqualTo(11);
    }
}
