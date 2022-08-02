package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {
    @Test
    @DisplayName("메소드 호출시, keyToValue가 header 구분자가 포함된 String으로 변환되어 반환된다.")
    void getHttpHeadersTest() {
        HttpHeader httpHeader = new HttpHeader(
            new LinkedHashMap() {{
                put("Host", "localhost:8080");
                put("Connection", "keep-alive");
                put("Accept", "*/*");
            }}
        );

        List<String> result = httpHeader.getHttpHeaders();

        assertAll(
            () -> assertThat(result.get(0)).isEqualTo("Host: localhost:8080"),
            () -> assertThat(result.get(1)).isEqualTo("Connection: keep-alive"),
            () -> assertThat(result.get(2)).isEqualTo("Accept: */*")
        );
    }

    @Test
    @DisplayName("cookie값을 입력하면 cookie 헤더에 해당 cookie값이 존재하는지 확인한다.")
    void hasCookieTest() {
        HttpHeader httpHeader = new HttpHeader(
            new LinkedHashMap() {{
                put("Host", "localhost:8080");
                put("Cookie", "keep-alive; logined=true; JSESSIONID=43E82F053892BB4D2F8E8E5DA90EFD00");

            }}
        );

        boolean result = httpHeader.hasCookie("logined=true");

        assertThat(result).isTrue();
    }
}