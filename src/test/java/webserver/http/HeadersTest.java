package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class HeadersTest {

    private static final String HEADER_NAME_CONTENT_TYPE = "content-type";
    private static final String HEADER_CONTENT_TYPE_APPLICATION_JSON = "application/json;";

    @DisplayName("Headers 파싱 테스트: content-type: application/json;")
    @ParameterizedTest()
    @ValueSource(strings = {HEADER_NAME_CONTENT_TYPE + ": " + HEADER_CONTENT_TYPE_APPLICATION_JSON})
    public void parseContentTypeHeaderLine(String headerLine) {
        Headers headers = new Headers();
        headers.addHeaderLine(headerLine);
        assertThat(headers.getHeaderValue(HEADER_NAME_CONTENT_TYPE)).isEqualTo(HEADER_CONTENT_TYPE_APPLICATION_JSON);
    }

    @DisplayName("Headers 파싱 테스트: null value exception")
    @ParameterizedTest()
    @NullSource
    public void parseNullHeaderLine(String headerLine) {
        Headers headers = new Headers();
        assertThatThrownBy(() -> {headers.addHeaderLine(headerLine);}).isInstanceOf(IllegalArgumentException.class);
    }

}
