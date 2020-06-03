package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringParserTest {
    @Test
    void parser(){
        QueryString requestLine = QueryStringParser.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getParameterString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.protocol.getVersion()).isEqualTo("1.1");
    }


    @Test
    void getParameter(){
//        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
//        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
//        assertThat(queryString.getParameter("password")).isEqualTo("password");
//        assertThat(queryString.getParameter("name")).isEqualTo("JaeSung");
    }
}
