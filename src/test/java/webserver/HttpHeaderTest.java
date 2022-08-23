package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HttpHeaderTest {

    @Test
    @DisplayName("잘되는지_확인")
    void  success() {
        //given
        HttpHeader httpHeader = new HttpHeader();
        List<String> requestHeaders = List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Cache-Control: max-age=0"
        );

        //when
        for (String requestHeader : requestHeaders) {
            httpHeader.addHeader(requestHeader);
        }

        //then
        assertThat(httpHeader.getValue("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeader.getValue("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeader.getValue("Cache-Control")).isEqualTo("max-age=0");
    }
}