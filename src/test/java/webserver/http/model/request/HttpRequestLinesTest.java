package webserver.http.model.request;

import exception.IllegalHttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HttpRequestLinesTest {

    @DisplayName("httpRequest 값이 null이 전달되었을 때 IllegalArgumentException이 발생합니다.")
    @Test
    void construct_validate1() {
        assertThatThrownBy(() -> new HttpRequestLines("")).isInstanceOf(IllegalHttpRequestException.class)
                .hasMessageContaining("httpRequest는 빈 값이 전달 될 수 없습니다.");
    }

    @DisplayName("httpRequest에는 줄바꿈 기준으로 두줄 이상 있어야 정상적인 호출입니다. (최소한 requestLine, requestHeader는 존재해야 합니다)")
    @Test
    void construct_validate2() {
        assertThatThrownBy(() -> new HttpRequestLines("test")).isInstanceOf(IllegalHttpRequestException.class)
                .hasMessageContaining("httpRequest는 적어도 두 줄 이상의 정보로 구성되어 있습니다.");
    }

    @DisplayName("httpRequest로 전달 받은 문자열을 줄바꿈 기준으로 나누어 requestLine, requestHeader, requestBody로 나누어 설정합니다.")
    @Test
    void construct() {
        String httpRequestText = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpRequestLines httpRequestLines = new HttpRequestLines(httpRequestText);

        assertThat(httpRequestLines.requestLine()).isNotNull();
        assertThat(httpRequestLines.requestHeader()).hasSize(5);
        assertThat(httpRequestLines.requestBody()).hasSize(1);
    }
}