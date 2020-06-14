package http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {
    @Test
    void parseGet() {
        RequestLine requestLine = RequestLineParser.parse("GET /docs/index.html HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parsePost() {
        RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parseQueryString() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?userId=flzl2008&password=password&name=HyeongGeun HTTP/1.1");
        Map<String, Object> queryString = requestLine.getQueryString();
        assertThat(queryString.get("userId")).isEqualTo("flzl2008");
        assertThat(queryString.get("password")).isEqualTo("password");
        assertThat(queryString.get("name")).isEqualTo("HyeongGeun");
    }

    @Test
    void getHttpMethod(){
        String methodName = "GET";
        assertThat(HttpMethod.valueOf(methodName)).isEqualTo(HttpMethod.GET);
    }
}