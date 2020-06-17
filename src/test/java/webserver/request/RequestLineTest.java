package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.FileType;
import webserver.Protocol;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

public class RequestLineTest {

    @DisplayName("[GET] HTTP 요청의 requestLine 파싱")
    @Test
    void parse_http_request_get() {

        // given
        String requestLineText = "GET /docs/index.html HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.of(requestLineText);

        // then
        assertThat(requestLine)
                .isEqualTo(new RequestLine(
                        GET, "/docs/index.html", new Protocol("HTTP", "1.1"), Collections.emptyMap()));
    }

    @DisplayName("[POST] HTTP 요청의 requestLine 파싱")
    @Test
    void parse_http_request_post() {

        // given
        String requestLineText = "POST /docs/index.html HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.of(requestLineText);

        // then
        assertThat(requestLine)
                .isEqualTo(new RequestLine(
                        POST, "/docs/index.html", new Protocol("HTTP", "1.1"), Collections.emptyMap()));
    }

    @DisplayName("HTTP 요청의 query string 파싱 - query string이 있을 경우")
    @Test
    void parse_query_string() {

        // given
        String requestLineText = "GET /users?userId=dowon&password=password&name=DoWonLee HTTP/1.1";
        Map<String, String> params = new HashMap<>();
        params.put("userId", "dowon");
        params.put("password", "password");
        params.put("name", "DoWonLee");

        // when
        RequestLine requestLine = RequestLine.of(requestLineText);

        // then
        assertThat(requestLine.getQueryParameters()).isEqualTo(params);
    }

    @DisplayName("HTTP 요청의 query string 파싱 - query string이 없을 경우")
    @Test
    void parse_empty_query_string_() {

        // given
        String requestLineText = "GET /users HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.of(requestLineText);

        // then
        assertThat(requestLine.getQueryParameters()).isEmpty();
    }

    @DisplayName("정적파일의 위치 반환")
    @Test
    void getPath() {

        // given
        String url = "/index.html";
        String requestLineText = "GET " + url + " HTTP/1.1";
        RequestLine requestLine = RequestLine.of(requestLineText);

        // when
        String path = requestLine.getPath();

        // then
        assertThat(path).isEqualTo("./templates" + url);
    }

    @DisplayName("요청받은 파일타입 반환")
    @Test
    void getFileType() {

        // given
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");

        // when
        FileType fileType = requestLine.getFileType();

        // then
        assertThat(fileType).isEqualTo(FileType.HTML);
    }

    @DisplayName("요청받은 파일타입 반환 - 파일타입을 명시하지 않았을 경우")
    @Test
    void getFileType_empty_file_type() {

        // given
        RequestLine requestLine = RequestLine.of("GET /users HTTP/1.1");

        // when
        FileType fileType = requestLine.getFileType();

        // then
        assertThat(fileType).isEqualTo(FileType.NONE);
    }
}
