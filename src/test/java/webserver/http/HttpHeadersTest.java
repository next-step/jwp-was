package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

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
        HttpHeaders httpHeaders = HttpHeaders.create();

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
        HttpHeaders httpHeaders = HttpHeaders.create();

        httpHeaders.addHeader("Content-Length: 59");

        assertThat(httpHeaders.hasContent()).isTrue();
        assertThat(httpHeaders.getContentLength()).isEqualTo(59);
    }

    @Test
    @DisplayName("response redirect를 위한 header를 생성한다.")
    void responseHeaderRedirect() {
        String redirectUrl = "/index.html";

        HttpHeaders actual = HttpHeaders.redirect(redirectUrl);

        Map<String, String> headers = actual.getHeaders();
        assertThat(actual.hasLocation()).isTrue();
        assertThat(headers.get(HttpHeader.LOCATION)).isEqualTo("http://localhost:8080/index.html");
    }
}
