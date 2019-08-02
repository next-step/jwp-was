package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class HttpHeadersTest {

    private static final String HEADER_NAME_CONTENT_TYPE = "content-type";
    private static final String HEADER_CONTENT_TYPE_APPLICATION_JSON = "application/json;";

    @DisplayName("HttpHeaders 파싱 테스트: content-type: application/json;")
    @ParameterizedTest()
    @ValueSource(strings = {HEADER_NAME_CONTENT_TYPE + ": " + HEADER_CONTENT_TYPE_APPLICATION_JSON})
    public void parseContentTypeHeaderLine(String headerLine) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeaderLine(headerLine);
        assertThat(httpHeaders.getHeaderValueFirst(HEADER_NAME_CONTENT_TYPE)).isEqualTo(HEADER_CONTENT_TYPE_APPLICATION_JSON);
    }

    @DisplayName("HttpHeaders 파싱 테스트: null value exception")
    @ParameterizedTest()
    @NullSource
    public void parseNullHeaderLine(String headerLine) {
        HttpHeaders httpHeaders = new HttpHeaders();
        assertThatThrownBy(() -> {
            httpHeaders.addHeaderLine(headerLine);}).isInstanceOf(IllegalArgumentException.class);
    }

}
