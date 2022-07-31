package webserver.http.request.header;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {
    @Test
    @DisplayName("HttpHeader 객체를 생성한다.")
    void create_HttpHeader() {
        HttpHeader header = new HttpHeader();
        assertThat(header).isNotNull().isInstanceOf(HttpHeader.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("header set 요청 값이 null 또는 비어있을 경우 예외가 발생한다.")
    void throw_exception_set_http_header_null_or_empty(String headerString) {
        HttpHeader header = new HttpHeader();
        assertThatThrownBy(() -> header.setField(headerString)).isInstanceOf(IllegalArgumentException.class);
    }
}