package enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {
    @Test
    @DisplayName("String 형태의 파라미터를 넘기면 해당하는 Enum 클래스를 찾을 수 있다.")
    void getHttpMethodTest() {
        String httpMethod = "GET";
        HttpMethod result = HttpMethod.getHttpMethod(httpMethod);

        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("존재하지 않는 String 형태의 파라미터를 넘기면 IllegalArgumentException이 발생한다")
    void getHttpMethodExceptionTest() {
        String httpMethod = "NOTEXIST";

        assertThrows(IllegalArgumentException.class, () -> HttpMethod.getHttpMethod(httpMethod));
    }
}