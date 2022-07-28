package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpHeaderTest {

    @Test
    void createDefaultResponseHeader() {
        HttpHeaders responseHeader = HttpHeaders.defaultResponseHeader();

        assertThat(responseHeader.getAttribute(HttpHeaders.CONTENT_TYPE)).isEqualTo(HttpHeaders.DEFAULT_CONTENT_TYPE);
    }

    @Test
    void createResponseHeader() {
        String[] headers = new String[]{"Content-Type: application/json", "Content-Length: 1024"};

        HttpHeaders httpHeaders = HttpHeaders.newInstance(headers, 0, headers.length);

        assertThat(httpHeaders.getAttribute("Content-Type")).isEqualTo("application/json");
        assertThat(httpHeaders.getAttribute("Content-Length")).isEqualTo("1024");
    }

    @DisplayName("header에 구분자가 유효한 속성을 전달하면 정상적으로 속성이 추가된다.")
    @Test
    void addHeaderContent() {
        HttpHeaders httpHeaders = HttpHeaders.defaultResponseHeader();

        httpHeaders.add("Content-Type: application/json");

        assertThat(httpHeaders.getAttribute("Content-Type")).isEqualTo("application/json");
    }

    @DisplayName("header에 구분자가 유효하지 않은 속성을 전달하면 예외를 던진다.")
    @Test
    void addHeaderContentFailed() {
        HttpHeaders httpHeaders = HttpHeaders.defaultResponseHeader();

        assertThatThrownBy(()-> httpHeaders.add("Content-Type=application/json"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("header에 유효한 key, value 매개변수를 전달하면 정상적으로 속성이 추가된다")
    @Test
    void addHeaderContentWithParams() {
        HttpHeaders httpHeaders = HttpHeaders.defaultResponseHeader();

        httpHeaders.add("Content-Type", "application/json");

        assertThat(httpHeaders.getAttribute("Content-Type")).isEqualTo("application/json");
    }
}
