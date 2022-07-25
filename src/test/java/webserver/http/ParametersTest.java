package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParametersTest {

    @DisplayName("Query String을 파싱한다.")
    @Test
    void parse() {
        RequestBody requestBody = new RequestBody("userId=javajigi&password=1234&name=JaeSung");
        assertAll(() -> {
            assertThat(requestBody.getParameter("userId")).isEqualTo("javajigi");
            assertThat(requestBody.getParameter("password")).isEqualTo("1234");
            assertThat(requestBody.getParameter("name")).isEqualTo("JaeSung");
            assertThat(requestBody.getParameter("email")).isNull();
        });
    }
}
