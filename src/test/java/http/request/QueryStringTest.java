package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("쿼리 스트링 클래스 테스트")
public class QueryStringTest {

    @Test
    @DisplayName("쿼리 스트링 파싱")
    void parse() {
        String query = "name1=value1&name2=value2";
        QueryString queryString = new QueryString(query);


        assertThat(queryString.getQueryString()).isEqualTo(query);
    }
}
