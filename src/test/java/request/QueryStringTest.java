package request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryStringTest {

    @Test
    @DisplayName("파라미터 값을 반환합니다")
    void getParameterTest() {
        QueryString queryString = new QueryString(Map.of("userId", "name"));

        assertThat(queryString.getParameter("userId")).isEqualTo("name");
    }

}
