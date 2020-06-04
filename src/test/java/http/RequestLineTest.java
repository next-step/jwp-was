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
}
