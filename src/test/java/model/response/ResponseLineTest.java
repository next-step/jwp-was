package model.response;

import enums.HttpStatus;
import model.WebProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseLineTest {
    @Test
    @DisplayName("getResponseLine()이 ResponseHeader의 형식을 반환하는지 테스트")
    void getResponseLineTest() {
        WebProtocol webProtocol = new WebProtocol("HTTP", "1.1");
        ResponseLine responseLine = new ResponseLine(HttpStatus.OK, webProtocol);

        String result = responseLine.getResponseLine();

        assertThat(result).isEqualTo("HTTP/1.1 200 OK");
    }
}
