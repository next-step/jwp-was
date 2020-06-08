package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("파라미터 관리 클래스")
class ParametersTest {

    @Test
    void newInstance() {
        assertThatCode(Parameters::newInstance).doesNotThrowAnyException();
    }
}