package http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineParserTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?a=b&c=d HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");

        assertThat(requestLine.getParameterMap()).containsKeys("a", "c");
        assertThat(requestLine.getParameterMap()).containsValues("b", "d");
    }

    @Test
    void getPath() {
        String path = RequestLineParser.getPath("/users?a=b&c=d");

        assertThat(path).isEqualTo("/users");
    }

    @Test
    void getParameterMapTest() {
        Map<String, String> parameterMap = RequestLineParser.getParameterMap("/users?a=b&c=d");

        assertThat(parameterMap).containsKeys("a", "c");
        assertThat(parameterMap).containsValues("b", "d");
    }

    @Test
    void getHttpMethodTest() {
        HttpMethod method = RequestLineParser.getMethod("GET");

        assertThat(method).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getHttpMethodEnumInvalidTest() {
        assertThatThrownBy(() -> {
            RequestLineParser.getMethod("GET!");
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
