package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParametersTest {

  @ParameterizedTest
  @CsvSource({"userId,javajigi", "password,password", "name,JaeSung"})
  @DisplayName("Parameters 을 파싱한다")
  void parametersParse(String key, String value) {
    Parameters parameters = Parameters
        .parse("userId=javajigi&password=password&name=JaeSung&noValue=");
    assertThat(parameters.getParameter(key)).isEqualTo(value);
  }

  @Test
  @DisplayName("value값이 없는 값은 빈값을 반환")
  void emptyValue() {
    Parameters parameters = Parameters
        .parse("name=JaeSung&noValue=");
    assertThat(parameters.getParameter("noValue")).isEqualTo("");
  }
}
