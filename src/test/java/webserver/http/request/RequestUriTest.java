package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.RequestUri;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    @DisplayName("path와 query string parse 테스트")
    @Test
    void request_path_query_string_parse() {
        RequestUri requestUri = RequestUri.parse("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(requestUri.getPath()).isEqualTo("/users");
        assertThat(requestUri.getParameter("userId")).isEqualTo("javajigi");
        assertThat(requestUri.getParameter("password")).isEqualTo("password");
        assertThat(requestUri.getParameter("name")).isEqualTo("JaeSung");
    }
}