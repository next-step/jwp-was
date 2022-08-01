package model.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {
    @Test
    @DisplayName("getResponseLine()이 ResponseHeader의 형식을 반환하는지 테스트")
    void getResponseLineTest() {
        ResponseLine responseLine = ResponseLine.httpOk();

        String result = responseLine.getResponseLine();

        assertThat(result).isEqualTo("HTTP/1.1 200 OK");
    }
}
