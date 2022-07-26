package webserver.http;

import exception.InvalidHeaderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHeaderTest {

    @DisplayName("헤더를 파싱한다.")
    @Test
    void parseHeader(){
        String[] input = {"Host: localhost:8080", "Connection: keep-alive", "Content-Length: 59"};

        RequestHeader header = new RequestHeader();
        for (String s : input) {
            header.add(s);
        }

        assertEquals("localhost:8080", header.getHost());
        assertEquals("keep-alive", header.getConnection());
        assertEquals(59, header.getContentLength());
    }

    @DisplayName("잘못된 헤더인 경우에는 예외를 리턴한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Host: ", ": ", "=="})
    void parseInvalidHeader(String input){
        RequestHeader requestHeaders = new RequestHeader();

        assertThatExceptionOfType(InvalidHeaderException.class)
                .isThrownBy(() -> requestHeaders.add(input));
    }
}
