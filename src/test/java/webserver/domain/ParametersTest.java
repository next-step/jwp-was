package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {
    @DisplayName("Query String을 파싱한다.")
    @Test
    void parametersTest() throws Exception {
        Parameters parameters = new Parameters("a=b&c=d&e");
        assertAll(
                () -> assertThat(parameters.getParameter("a")).isEqualTo("b"),
                () -> assertThat(parameters.getParameter("c")).isEqualTo("d"),
                () -> assertThat(parameters.getParameter("e")).isEqualTo("")
        );
    }

}
