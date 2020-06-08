package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeadersTest {

    @Test
    @DisplayName("문자열인 헤더 정보들로 Header 객체를 정상적으로 생성할 수 있다")
    void test1() {
        String[] input = {"Host: localhost:8080", "Connection: keep-alive", "Origin: http://localhost:8080"};

        Headers header = new Headers();

        assertThat(header).isNotNull();
    }

    @Test
    @DisplayName("Header 객체에서 헤더 이름으로 값을 가져올 수 있다")
    void test2() {
        final String[] input = {"Host: localhost:8080", "Connection: keep-alive", "Origin: http://localhost:8080"};
        final Headers header = new Headers();

        final String HostValue = header.getValue("Host");
        final String ConnectionValue = header.getValue("Connection");
        final String OriginValue = header.getValue("Origin");

        assertThat(HostValue).isEqualTo("localhost:8080");
        assertThat(ConnectionValue).isEqualTo("keep-alive");
        assertThat(OriginValue).isEqualTo("http://localhost:8080");
    }

}