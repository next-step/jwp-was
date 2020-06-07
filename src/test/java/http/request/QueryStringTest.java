package http.request;

import http.request.QueryString;
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
        QueryString queryString = QueryString.init("name1=value1&name2=value2");

        assertThat(queryString.get("name1")).isEqualTo("value1");
        assertThat(queryString.get("name2")).isEqualTo("value2");
        assertThat(queryString.get("name3")).isEqualTo(null);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("null 이나 빈 문자열이 전달되더라도 빈 Map을 가진 QueryString 객체를 리턴한다.")
    void parseNull(final String queryString) {
        assertThatCode(() -> QueryString.init(queryString)).doesNotThrowAnyException();
    }
}
