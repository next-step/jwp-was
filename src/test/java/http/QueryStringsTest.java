package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringsTest {
    @DisplayName("QueryStrings 생성 - GET")
    @Test
    public void createQueryStrings() {
        //given
        String path = "/users?userId=javajigi&password=password&name=JaeSung";

        //when
        QueryStrings queryStrings = new QueryStrings(path);

        //then
        assertThat(queryStrings.getQueryStrings().get("userId")).isEqualTo("javajigi");
        assertThat(queryStrings.getQueryStrings().get("password")).isEqualTo("password");
        assertThat(queryStrings.getQueryStrings().get("name")).isEqualTo("JaeSung");
    }
}
