package http;

import http.requestline.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
