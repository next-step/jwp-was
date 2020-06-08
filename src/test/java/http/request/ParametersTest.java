package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("파라미터 관리 클래스")
class ParametersTest {

    @Test
    @DisplayName("정적 팩토리 메서드로 생성")
    void newInstance() {
        assertThatCode(Parameters::newInstance).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿼리 스트링, 혹은 body를 받아서 파라미터에 추가")
    void parse() throws IOException {
        String query = "GET test?name1=value1&name2=value2 HTTP/1.1";

        Parameters parameters = Parameters.parse(RequestLine.parse(query));

        assertThat(parameters.getParameter("name1")).isEqualTo("value1");
        assertThat(parameters.getParameter("name2")).isEqualTo("value2");
    }

    @Test
    @DisplayName("쿼리 스트링, 혹은 body를 받아서 업데이트")
    void update() {
        Parameters parameters = Parameters.newInstance();
        String body = "name1=value1&name2=value2";

        parameters.addParameters(body);

        assertThat(parameters.getParameter("name1")).isEqualTo("value1");
        assertThat(parameters.getParameter("name2")).isEqualTo("value2");
    }
}