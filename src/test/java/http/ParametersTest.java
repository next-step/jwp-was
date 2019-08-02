package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParametersTest {

  @ParameterizedTest
  @CsvSource({
      "userId=javajigi&password=password&name=JaeSung, javajigi, password, JaeSung"
  })
  @DisplayName("Parameters 을 파싱한다")
  void parametersParse(String queryString, String expectedUserId, String expectedPassword,
      String expectedName) {
    Parameters parameters = Parameters.parse(queryString);

    assertThat(parameters.getParameter("userId")).isEqualTo(expectedUserId);
    assertThat(parameters.getParameter("password")).isEqualTo(expectedPassword);
    assertThat(parameters.getParameter("name")).isEqualTo(expectedName);
  }

  @Test
  @DisplayName("key값만 있고 value값이 없는 parameter는 빈값을 반환")
  void emptyValue() {
    Parameters parameters = Parameters
        .parse("name=JaeSung&noValue=");
    assertThat(parameters.getParameter("noValue")).isEqualTo("");
  }
}
