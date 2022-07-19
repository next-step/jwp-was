package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {
    @Test
    @DisplayName("GET요청에 대한 RequestLine을 파싱한다.")
    void GET요청_파싱_테스트() {
        //given
        String requestLine = "GET /users HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(requestLine);
        //then
        assertAll(
                () -> assertThat(requestLineParser.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLineParser.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLineParser.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLineParser.getVersion()).isEqualTo("1.1")
        );
    }
}
