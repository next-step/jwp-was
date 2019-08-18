package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestLineTest {
    @DisplayName("HttpRequestLine 생성 - queryString 없는경우")
    @ParameterizedTest
    @CsvSource({"GET /index.html HTTP/1.1,GET,/index.html"
            , "POST /user/create HTTP/1.1,POST,/user/create"})
    void noQueryString(String reqLine, String method, String url) {
        //when
        HttpRequestLine httpRequestLine = HttpRequestLine.of(reqLine);

        //then
        assertThat(httpRequestLine.getMethod()).isEqualTo(method);
        assertThat(httpRequestLine.getRequestUri()).isEqualTo(url);
        assertThat(httpRequestLine.getQueryString()).isEqualTo("noQueryString");
    }

    @DisplayName("HttpRequestLine 생성 - queryString 있는경우")
    @ParameterizedTest
    @CsvSource({"GET /users/login?userId=123&password=456 HTTP/1.1,GET,userId=123&password=456"})
    void withQueryString(String reqLine, String method, String queryString) {
        //when
        HttpRequestLine httpRequestLine = HttpRequestLine.of(reqLine);

        //then
        assertThat(httpRequestLine.getMethod()).isEqualTo(method);
        assertThat(httpRequestLine.getQueryString()).isEqualTo(queryString);
    }
}
