package utils;

import http.requests.HttpRequestHeader;
import http.requests.parameters.FormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http 요청을 파싱하는 클래스 테스트")
class HttpRequestParserTest {

    private List<String> rawRequestHeaders;

    @BeforeEach
    void setUp() {
        rawRequestHeaders = new ArrayList<>();
        rawRequestHeaders.add("Host: localhost:8080");
        rawRequestHeaders.add("Content-Type: application/x-www-form-urlencoded");
        rawRequestHeaders.add("x-amz-replication-status: COMPLETED");
    }

    @DisplayName("요청으로부터 헤더를 파싱한다.")
    @Test
    void parse_headers_from_request() {
        final HttpRequestHeader header = HttpRequestParser.parseHeaders(rawRequestHeaders);
        assertThat(header).isNotNull();
    }

    @DisplayName("HttpHeader 타입으로 헤더 정보를 얻어올 수 있어야 한다.")
    @Test
    void get_header_with_type() {
        final HttpRequestHeader header = HttpRequestParser.parseHeaders(rawRequestHeaders);
        assertThat(header.getHeader(HttpHeader.HOST)).isEqualTo("localhost:8080");
        assertThat(header.getHeader(HttpHeader.CONTENT_TYPE)).isEqualTo("application/x-www-form-urlencoded");
    }

    @DisplayName("타입 없이 Low String으로도 헤더 정보를 얻어올 수 있어야 한다.")
    @Test
    void get_header_with_String() {
        final HttpRequestHeader header = HttpRequestParser.parseHeaders(rawRequestHeaders);
        assertThat(header.getHeader("x-amz-replication-status")).isEqualTo("COMPLETED");
    }

    @DisplayName("요청으로부터 Form Data를 파싱한다.")
    @Test
    void parse_form_data_from_request() {
        final String formData = "userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com";
        final FormData form = HttpRequestParser.parseFormData(formData);
        assertThat(form).isNotNull();
    }

    @DisplayName("파싱된 폼 데이터로 부터 생성된 객체에서 값이 잘 추출되는지 확인한다.")
    @Test
    void get_form_data() {
        final String formData = "userId=hyeyoom&password=1234abcd&name=Chiho+Won&email=neoul_chw%40icloud.com";
        final FormData form = HttpRequestParser.parseFormData(formData);
        assertThat(form.get("userId")).isEqualTo("hyeyoom");
        assertThat(form.get("password")).isEqualTo("1234abcd");
        assertThat(form.get("name")).isEqualTo("Chiho Won");
        assertThat(form.get("email")).isEqualTo("neoul_chw@icloud.com");
    }
}