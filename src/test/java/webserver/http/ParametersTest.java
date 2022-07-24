package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.Parameters;

class ParametersTest {

    @DisplayName("Query String을 파싱한다.")
    @Test
    void parse() {
        Parameters parameters = new Parameters("userId=javajigi&password=1234&name=JaeSung");
        assertAll(() -> {
            assertThat(parameters.getParameter("userId")).isEqualTo("javajigi");
            assertThat(parameters.getParameter("password")).isEqualTo("1234");
            assertThat(parameters.getParameter("name")).isEqualTo("JaeSung");
            assertThat(parameters.getParameter("email")).isNull();
        });
    }
}
