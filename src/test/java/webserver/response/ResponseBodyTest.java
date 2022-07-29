package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseBodyTest {

    @DisplayName("ResponseBody는 비어있을 수 있다")
    @Test
    void empty_response_body() {
        final ResponseBody responseBody = new ResponseBody("");

        assertThat(responseBody).isEqualTo(ResponseBody.EMPTY_RESPONSE_BODY);
        final byte[] bytes = responseBody.getBytes();
        System.out.println("bytes = " + Arrays.toString(bytes));
    }

    @DisplayName("ResponseBody 객체 생성")
    @Test
    void create() {
        final ResponseBody responseBody = new ResponseBody("<html></html>");

        assertThat(responseBody).isEqualTo(new ResponseBody("<html></html>"));
    }

    @DisplayName("byte 배열을 리턴한다")
    @Test
    void return_byte_array() {
        final ResponseBody responseBody = new ResponseBody("<html></html>");

        assertThat(responseBody.getBytes()).isEqualTo(new byte[]{60, 104, 116, 109, 108, 62, 60, 47, 104, 116, 109, 108, 62});
    }
}
