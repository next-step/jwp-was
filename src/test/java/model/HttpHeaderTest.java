package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {

    @Test
    void 빌더_생성자_만들기() {

        final String contentType = "testType";
        int contentLength = 10;

        final HttpHeader result = new HttpHeader.HttpHeaderBuilder()
                .addContentType(contentType)
                .addContentLength(contentLength)
                .build();

        assertThat(result.getValue("Content-Type")).isEqualTo(contentType + ";charset=utf-8");
        assertThat(result.getValue("Content-Length")).isEqualTo(String.valueOf(contentLength));
    }
}
