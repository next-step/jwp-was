package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    @DisplayName("path와 query string parse 테스트")
    @Test
    void request_path_query_string_parse() {
        RequestUri requestUri = RequestUri.parse("/users?userId=javajigi&password=password&name=JaeSung");
        QueryParameter queryParameter = requestUri.getQueryParameter();

        assertThat(requestUri.getPath()).isEqualTo("/users");
        assertThat(queryParameter.getQueryParameters().size()).isEqualTo(3);
    }
}