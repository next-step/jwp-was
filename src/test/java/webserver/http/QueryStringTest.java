package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {

    @Test
    void parse() {
        String requestLineString = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLine.parse(requestLineString);
        QueryString queryString = requestLine.getQueryString();

        assertThat(queryString.parameterSize()).isEqualTo(3);
        assertThat(queryString.get("userId")).isEqualTo("javajigi");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("JaeSung");
    }
}
