package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("쿼리 스트링 클래스 테스트")
public class QueryStringTest {

    @Test
    void parse() {
        QueryString queryString = new QueryString("name1=value1&name2=value2");

        assertThat(queryString.get("name1")).isEqualTo("value1");
        assertThat(queryString.get("name2")).isEqualTo("value2");
        assertThat(queryString.get("name3")).isEqualTo(null);
    }
}
