package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueryParamsTest {

    @DisplayName("쿼리파라미터를 생성할 수 있다.")
    @Test
    void of() {
        var parameter = "userId=javajigi&password=password&name=JaeSung";

        var queryParams = QueryParams.of(parameter);

        assertAll(
            () -> assertThat(queryParams.get("userId")).isEqualTo("javajigi"),
            () -> assertThat(queryParams.get("password")).isEqualTo("password"),
            () -> assertThat(queryParams.get("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("키가 충돌되면 마지막 값을 반환한다.")
    @Test
    void get() {
        var parameter = "userId=1&userId=2&userId=3";

        var queryParams = QueryParams.of(parameter);

        assertThat(queryParams.get("userId")).isEqualTo("3");
    }
}