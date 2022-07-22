package model;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryStringTest {

    @Test
    @DisplayName("파라미터가 없을 때")
    void isNotExistParameter() {
        QueryString queryString = QueryString.parser("");

        assertThat(queryString.toString()).isEmpty();
    }

    @Test
    @DisplayName("파라미터가 있을 경우 검증")
    void isExistParameter() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("name", "김배민");
        parameter.put("age", "3");
        QueryString 비교값 = new QueryString(parameter);

        QueryString queryString = QueryString.parser("name=김배민&age=3");

        assertThat(queryString).isEqualTo(비교값);
    }

}
