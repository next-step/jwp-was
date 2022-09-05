package webserver.http;

import exception.NotFoundHttpHeaderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Http Header 테스트")
class HttpHeadersTest {

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "Host: localhost:8080",
                    "Connection: keep-alive",
                    "Content-Length: 59",
                    "Content-Type: application/x-www-form-urlencoded",
                    "Accept: */*"
            }
    )
    @DisplayName("Http Header를 파싱한다.")
    void parseHeader(String headerLine) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.addHeader(headerLine);

        String[] expects = headerLine.split(": ");

        assertThat(httpHeaders.getHeaders()).hasSize(1);
        assertThat(httpHeaders.getHeaders()).contains(
                entry(expects[0], expects[1])
        );
    }

    @Test
    @DisplayName("Content-Length header 가 있을 경우 본문 길이를 가져온다.")
    void getContentLength() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.addHeader("Content-Length: 59");

        assertThat(httpHeaders.getContentLength()).isEqualTo(59);
    }

    @Test
    @DisplayName("Content-Length header 가 없을 경우 Not Found Http Header Exception 발생")
    void getContentLengthFailed() {
        assertThatExceptionOfType(NotFoundHttpHeaderException.class)
                .isThrownBy(() -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.getContentLength();
                })
                .withMessage("not found http header: (Content-Length)");
    }
}
