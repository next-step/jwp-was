package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.request.Cookies;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        HttpHeaders httpHeaders = HttpHeaders.init();

        httpHeaders.addRequestHeader(headerLine);

        String[] expects = headerLine.split(": ");

        assertThat(httpHeaders.getHeader(expects[0])).isEqualTo(expects[1]);
    }

    @Test
    @DisplayName("Content-Length header 가 있을 경우 본문 길이를 가져온다.")
    void getContentLength() {
        HttpHeaders httpHeaders = HttpHeaders.init();

        httpHeaders.addRequestHeader("Content-Length: 59");

        assertThat(httpHeaders.hasContent()).isTrue();
        assertThat(httpHeaders.getContentLength()).isEqualTo(59);
    }

    @Test
    @DisplayName("response redirect를 위한 header를 생성한다.")
    void responseHeaderRedirect() {
        String redirectUrl = "/index.html";

        HttpHeaders actual = HttpHeaders.redirect(redirectUrl);

        assertThat(actual.hasLocation()).isTrue();
        assertThat(actual.getHeader(HttpHeader.LOCATION)).isEqualTo("http://localhost:8080/index.html");
    }

    @Test
    @DisplayName("response spec 을 만들어주기 위한 포맷을 담는 List를 생성한다.")
    void getOutputHeaders() {
        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8");
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, "29");

        List<String> actual = httpHeaders.getOutputHeaders();

        assertThat(actual).contains(
                "Content-Type: text/html;charset=utf-8",
                "Content-Length: 29"
        );
    }

    @Test
    @DisplayName("요청 헤더에 Cookie 가 있을 경우 이를 파싱한 Cookies 를 리턴한다.")
    void getCookies() {
        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addRequestHeader("Cookie: logined=true; Path=/");

        Cookies actual = httpHeaders.getCookies();

        assertThat(actual.getCookiesSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("요청 헤더에 Cookie 가 없을 경우 비어있는 Cookies를 리턴한다.")
    void getEmptyCookies() {
        HttpHeaders httpHeaders = HttpHeaders.init();

        Cookies actual = httpHeaders.getCookies();

        assertThat(actual.getCookiesSize()).isEqualTo(0);
    }
}
