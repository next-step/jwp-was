package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriFactoryTest {

    @DisplayName("path query string 파싱")
    @Test
    void pathParing() {
        Map<String, String> queryStrings = new HashMap<>();
        queryStrings.put("userId", "javajigi");
        queryStrings.put("password", "password");
        queryStrings.put("name", "JaeSung");

        RequestUriFactory requestUri = RequestUriFactory.parse("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(requestUri.getPath()).isEqualTo("/users");
        assertThat(requestUri.getQueryParams()).isEqualTo(queryStrings);
    }


    @DisplayName("query string key가 없을 때 포함하지 않는다")
    @Test
    void queryStringEmptyKey() {
        RequestUriFactory requestUri = RequestUriFactory.parse("/users?userId=javajigi&password=password&JaeSung");

        Map<String, String> queryStrings = new HashMap<>();
        queryStrings.put("userId", "javajigi");
        queryStrings.put("password", "password");

        assertThat(requestUri.getPath()).isEqualTo("/users");
        assertThat(requestUri.getQueryParams()).isEqualTo(queryStrings);
    }
}