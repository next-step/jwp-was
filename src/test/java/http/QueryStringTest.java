package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class QueryStringTest {

    @Test
    @DisplayName("유효한 쿼리를 입력했을때 쿼리 값이 잘 파싱되는지 테스트")
    void getParameterTest() {
        QueryString queryString = QueryString.of("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getPrameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getPrameter("password")).isEqualTo("password");
        assertThat(queryString.getPrameter("name")).isEqualTo("JaeSung");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "a="})
    @DisplayName("유효하지 않은 입력값으로 QueryString 을 생성 시에 지정한 예외가 발생하는지 테스트")
    void getInvalidParameterTest(String queryInput) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            QueryString.of(queryInput);
        }).withMessage(QueryString.ILLEGAL_QUERY);
    }
}
