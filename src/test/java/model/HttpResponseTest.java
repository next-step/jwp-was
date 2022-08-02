package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HttpResponse 테스트")
class HttpResponseTest {

    @DisplayName("textHtml Response 생성")
    @Test
    void textHtmlResponse() {
        String contents = "contents";
        byte[] contentsBytes = contents.getBytes(StandardCharsets.UTF_8);

        HttpResponse response = HttpResponse.of(
                HttpStatusCode.OK,
                ResponseHeader.text(contentsBytes.length, "/index.html"),
                contentsBytes
        );

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry("Content-Type", "text/html;charset=utf-8"),
                        Map.entry("Content-Length", contentsBytes.length)),
                () -> assertThat(response.getBody()).isEqualTo(contentsBytes)
        );
    }

}
