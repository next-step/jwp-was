package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    private RequestMethod requestMethod;
    private Protocol protocol;

    @BeforeEach
    void setUp() {
        requestMethod = new RequestMethodGet("/users");
        protocol = new Protocol("HTTP/1.1");
    }

    @Test
    @DisplayName("객체 생성")
    void create() {
        // give
        RequestLine actualRequestLine = new RequestLine(requestMethod, protocol);
        // when
        boolean same = actualRequestLine.equals(new RequestLine(new RequestMethodGet("/users"), new Protocol("HTTP/1.1")));
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("메소드 이름 테스트")
    void getMethodNameByRequestLine() {
        // give
        RequestLine requestLine = new RequestLine(requestMethod, protocol);
        String actualMethodName = requestLine.getMethodName();
        // when
        boolean same = actualMethodName.equals(requestMethod.getMethodName());
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("패스 테스트")
    void getPathByRequestLine() {
        // give
        RequestLine requestLine = new RequestLine(requestMethod, protocol);
        String actualPath = requestLine.getPath();
        // when
        boolean same = actualPath.equals(requestMethod.getPath());
        // then
        assertThat(same).isTrue();
    }
}
