package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestPathTest {

    @DisplayName("path와 query string parse 테스트")
    @Test
    void request_path_query_string_parse() {
        RequestPath requestPath = RequestPath.parse("/users?userId=javajigi&password=password&name=JaeSung");
        QueryString queryString = requestPath.getQueryString();

        assertThat(requestPath.getPath()).isEqualTo("/users");
        assertThat(queryString.getQueryParameters().size()).isEqualTo(3);
    }
}