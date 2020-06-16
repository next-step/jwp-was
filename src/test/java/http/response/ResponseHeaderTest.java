package http.response;

import http.common.HeaderField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @Test
    @DisplayName("ResponseHeader 객체를 생성할 수 있다")
    void createResponseHeader() {
        new ResponseHeader();
    }

    @Test
    @DisplayName("ResponseHeader 객체에 HeaderField로 헤더를 추가할 수 있다")
    void addTest() {
        final String name = "Content-Type";
        final String value = "text/html";
        final HeaderField headerField = new HeaderField(name, value);
        final ResponseHeader responseHeader = new ResponseHeader();

        responseHeader.addHeader(headerField);

        assertThat(responseHeader.getValue(name)).hasValue(value);
    }

    @Test
    @DisplayName("ResponseHeader 객체에 키-밸류로 헤더를 추가할 수 있다")
    void addHeader() {
        final String name = "Content-Type";
        final String value = "text/html";
        final ResponseHeader responseHeader = new ResponseHeader();

        responseHeader.addHeader(name, value);

        assertThat(responseHeader.getValue(name)).hasValue(value);
    }

    @Test
    @DisplayName("ResponseHeader 객체에서 이름에 해당하는 값을 가져올 수 있다")
    void getTest() {
        final String name = "Content-Type";
        final String value = "text/html";
        final HeaderField headerField = new HeaderField(name, value);
        final ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.addHeader(headerField);

        final Optional<String> result = responseHeader.getValue(name);

        assertThat(result).hasValue(value);
    }

    @Test
    @DisplayName("ResponseHeader 객체에서 존재하지 않는 헤더 값을 요청할 경우 Optional.empty 을 반환한다")
    void returnEmpty() {
        final ResponseHeader responseHeader = new ResponseHeader();
        final String name = "Content-Type";

        final Optional<String> result = responseHeader.getValue(name);

        assertThat(result).isEmpty();
    }

}