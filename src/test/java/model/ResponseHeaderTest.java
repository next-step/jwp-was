package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ResponseHeader 테스트")
class ResponseHeaderTest {

    @DisplayName("textHtml Header 생성 테스트")
    @Test
    void textHtml() {
        String contents = "contents";
        byte[] contentsBytes = contents.getBytes(StandardCharsets.UTF_8);

        ResponseHeader responseHeader = ResponseHeader.text(contentsBytes.length, "/index.html");

        assertThat(responseHeader.getHeaders()).contains(
                Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8"),
                Map.entry(HttpHeaders.CONTENT_LENGTH, contentsBytes.length)
        );

    }

}
