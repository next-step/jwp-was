package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class QueryStringTest {

    @Test
    void parse_query_string() {
        QueryString queryString = QueryString.of("a=1&b=2");
        assertThat(queryString.get("a")).isEqualTo("1");
        assertThat(queryString.get("b")).isEqualTo("2");
    }

    @Test
    void decoding() {
        QueryString queryString = QueryString.of("%EC%9D%B4%EB%A6%84=%ED%99%8D%EA%B8%B8%EB%8F%99");
        assertThat(queryString.get("이름")).isEqualTo("홍길동");
    }

    @Test
    void not_contain() {
        QueryString queryString = QueryString.of("a=1&b=2");
        assertThat(queryString.get("c")).isNull();
    }
}
