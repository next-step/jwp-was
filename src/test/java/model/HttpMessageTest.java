package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class HttpMessageTest {

    private final String BODY_SEPARATOR = "";

    @DisplayName("format validation 검증")
    @Test
    void validationTest() {
        List<String> wrongHttpMessageData1 = Collections.emptyList();
        Assertions.assertThatThrownBy(() -> new HttpMessage(wrongHttpMessageData1))
                .isInstanceOf(IllegalArgumentException.class);

        List<String> wrongHttpMessageData2 = List.of(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                BODY_SEPARATOR,
                BODY_SEPARATOR,
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        Assertions.assertThatThrownBy(() -> new HttpMessage(wrongHttpMessageData2))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
