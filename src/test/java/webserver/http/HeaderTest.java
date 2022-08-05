package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HeaderTest {
    @Test
    @DisplayName("HttpHeader 객체를 생성한다.")
    void create_HttpHeader() {
        Header header = new Header();
        assertThat(header).isNotNull().isInstanceOf(Header.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("header set 요청 값이 null 또는 비어있을 경우 예외가 발생한다.")
    void throw_exception_set_http_header_null_or_empty(String headerString) {
        Header header = new Header();
        assertThatThrownBy(() -> header.addField(headerString)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Cookie 셋팅 요청 값이 null 일 경우 예외가 발생한다.")
    void throw_exception_setCookie_null(String cookieString) {
        Header header = new Header();
        assertThatThrownBy(() -> header.setCookie(cookieString)).isInstanceOf(IllegalArgumentException.class);
    }
}