package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("파라미터 관리 클래스")
class ParametersTest {

    @Test
    @DisplayName("정적 팩토리 메서드로 생성")
    void newInstance() {
        assertThatCode(Parameters::newInstance).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿼리 스트링, 혹은 body를 받아서 파라미터에 추가")
    void parse() {
        String query = "name1=value1&name2=value2";

        Parameters parameters = Parameters.newInstance();
        parameters.parse(query);

        assertThat(parameters.get("name1")).isEqualTo("value1");
        assertThat(parameters.get("name2")).isEqualTo("value2");
    }
}