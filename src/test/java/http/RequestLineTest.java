package http;

import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @ParameterizedTest
    @DisplayName("RequestLine을 생성하고 비교")
    @CsvSource(value = {
            "GET /user HTTP/1.1:GET:/user",
            "POST /user HTTP/1.1:POST:/user"
    }, delimiter = ':')
    void getRequestLineTest(final String request,
                            final HttpMethod httpMethod,
                            final String path) {
        final RequestLine requestLine = RequestLine.of(request);
        assertThat(requestLine.getMethod()).isEqualTo(httpMethod);
        assertThat(requestLine.getPath()).isEqualTo(path);
    }

    @ParameterizedTest
    @DisplayName("RequestLine 생성하고 queryString 비교")
    @CsvSource(value = {
            "GET /user?userId=jinwoo&password=1q2w3e4r HTTP/1.1:userId=jinwoo&password=1q2w3e4r",
            "POST /user?userId=jinwoo&password=1q2w3e4r HTTP/1.1:userId=jinwoo&password=1q2w3e4r"
    }, delimiter = ':')
    void getRequestParam(final String request, final String queryString) {
        final RequestLine requestLine = RequestLine.of(request);
        assertThat(requestLine.getQueryString()).isEqualTo(queryString);
    }
}
