package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class QueryStringTest {

  QueryString queryString;

  @BeforeEach
  void 생성() {
    queryString = QueryString.parse("userId=javajigi&password=password&name=JaeSung&noValue=");
  }

  @ParameterizedTest
  @CsvSource({"userId,javajigi", "password,password", "name,JaeSung"})
  void 파라미터확인(String key, String value) {
    assertThat(queryString.getParam(key)).isEqualTo(value);
  }

  @Test
  void key값만_들어오면_빈값을_준다() {
    assertThat(queryString.getParam("noValue")).isEqualTo("");
  }
}
