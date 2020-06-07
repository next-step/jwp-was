package http.requestLine;

import http.request.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodTest {
    @DisplayName("ReuqestLine에서 Method 추출")
    @Test
    void extractMethod() {
        //given
        String requestLine = "GET /user/create?name=seung&email=email@gmail.com HTTP/1.1";

        //when
        Method method = Method.match(requestLine);

        //then
        assertThat(method).isEqualTo(Method.GET);
    }

    @DisplayName("RequestLine이 QueryString 을 가지고 있는지 검증")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    void hasQueryStrings(String method){
        //given
        String requestLine = method + " /user/create?id=1 HTTP/1.1";

        //when
        boolean hasQueryString = Method.hasQueryString(requestLine);

        //then
        assertThat(hasQueryString).isTrue();
    }
}
