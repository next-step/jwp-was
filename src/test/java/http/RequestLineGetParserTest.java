package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineGetParserTest {

    @Test
    public void parseGet() {
        RequestLineGet requestLineGet = RequestLineParser.parse("GET /users HTTP/1.1");

        assertThat(requestLineGet).isEqualTo(RequestLineGet.of("GET", "/users", "HTTP/1.1"));
        assertThat(requestLineGet.getProtocol()).isEqualTo(Protocol.of("HTTP/1.1"));
    }

    @Test
    public void parsePost() {
        RequestLineGet requestLineGet = RequestLineParser.parse("POST /users HTTP/1.1");

        assertThat(requestLineGet).isEqualTo(RequestLineGet.of("POST", "/users", "HTTP/1.1"));
        assertThat(requestLineGet.getProtocol()).isEqualTo(Protocol.of("HTTP/1.1"));
    }

    @Test
    public void queryString() {
        String source = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        RequestLineGet requestLineGet = RequestLineParser.parse(source);
        QueryStrings queryStrings = requestLineGet.getQueryStrings();

        assertThat(queryStrings.getQueryStrings())
                .contains(
                        new QueryString("userId", "javajigi"),
                        new QueryString("password", "password"),
                        new QueryString("name", "JaeSung")
                );

    }


}
