package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderTest {

    @Test
    @DisplayName("문자열인 헤더 정보들로 Header 객체를 정상적으로 생성할 수 있다")
    void test1() {
        String[] input = {"Host: localhost:8080", "Connection: keep-alive", "Origin: http://localhost:8080"};

        Header header = new Header(input);

        assertThat(header).isNotNull();
    }

}