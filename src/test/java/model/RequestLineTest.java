package model;

import constant.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineTest {

    @Test
    @DisplayName("Get 메소드 테스트 하기")
    void requestLineByGet() {
        String line = "GET /nextstep?name=김배민&age=3 HTTP/1.1";

        RequestLine requestLine = RequestLine.from(line);
        QueryString queryString = requestLine.getQueryString();

        assertAll(() -> {
            assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
            assertThat(requestLine.getPath()).isEqualTo("/nextstep");
            assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
            assertThat(requestLine.getVersion()).isEqualTo("1.1");
            assertThat(queryString.getParameter("name")).isEqualTo("김배민");
            assertThat(queryString.getParameter("age")).isEqualTo("3");
        });
    }

    @Test
    @DisplayName("POST 메소드 테스트 하기")
    void requestLineByPost() {
        String line = "POST /nextstep?name=김배민&age=3 HTTP2/1.2";

        RequestLine requestLine = RequestLine.from(line);
        QueryString queryString = requestLine.getQueryString();

        assertAll(() -> {
            assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(requestLine.getPath()).isEqualTo("/nextstep");
            assertThat(requestLine.getProtocol()).isEqualTo("HTTP2");
            assertThat(requestLine.getVersion()).isEqualTo("1.2");
            assertThat(queryString.getParameter("name")).isEqualTo("김배민");
            assertThat(queryString.getParameter("age")).isEqualTo("3");
        });

    }

}
