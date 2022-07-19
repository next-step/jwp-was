package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestPathTest {

    @CsvSource(value = {"/users:/users", "/users?userId=hjjang:/users", "search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EB%A7%9B%EC%A7%91:search.naver.com/search.naver"}, delimiter = ':')
    @ParameterizedTest
    void REQUEST_PATH_생성(String requestPath, String expectPath) {
        assertThat(new RequestPath(requestPath).getPath()).isEqualTo(expectPath);
    }
}