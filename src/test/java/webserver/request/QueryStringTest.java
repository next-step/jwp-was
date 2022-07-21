package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("쿼리스트링")
class QueryStringTest {

    @Test
    @DisplayName("파싱 테스트(성공)")
    void ParsingTest() throws Exception {
        QueryString queryString = QueryString.findQueryString("userId=javajigi&password=password");

        List<Map<String, String>> queryStrings = new ArrayList<>();
        queryStrings.add(Map.of("userId", "javajigi"));
        queryStrings.add(Map.of("password", "password"));

        assertThat(queryString.getQueryString().get(0)).isEqualTo(queryStrings.get(0));
        assertThat(queryString.getQueryString().get(1)).isEqualTo(queryStrings.get(1));
    }

}