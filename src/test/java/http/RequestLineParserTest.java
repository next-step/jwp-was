package http;

import org.junit.jupiter.api.Test;

import static http.HttpMethodType.GET;
import static http.HttpMethodType.POST;
import static org.assertj.core.api.Assertions.assertThat;


public class RequestLineParserTest {

    @Test
    void parseGetMethod() {
        RequestLine line = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(GET);
        assertThat(line.getPath()).isEqualTo("/users");
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @Test
    void parsePostMethod() {
        RequestLine line = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(line.getMethod()).isEqualTo(POST);
        assertThat(line.getPath()).isEqualTo("/users");
        assertThat(line.getProtocol()).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @Test
    void parseGetQueryParsing() {
        QueryString queryString = RequestLineParser.parseQueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getParam().get("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParam().get("password")).isEqualTo("password");
        assertThat(queryString.getParam().get("name")).isEqualTo("JaeSung");
    }
}
