package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.Query;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryTest {

    @Test
    void from() {
        // when
        Query query = Query.from("userId=javajigi&password=password&name=JaeSung");

        // then
        assertThat(query.getQueryMap()).containsOnly(
                Map.entry("userId", "javajigi"),
                Map.entry("password", "password"),
                Map.entry("name", "JaeSung")
        );
    }
}