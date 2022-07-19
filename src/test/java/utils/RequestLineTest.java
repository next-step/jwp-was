package utils;

import model.HttpMethodType;
import model.UnSupportMethodType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineTest {

    @Test
    void GET_요청을_파싱한다() {

        String request = "GET /users HTTP/1.1";

        final RequestLine requestLine= new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethodType.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void POST_요청을_파싱한다() {

        String request = "POST /users HTTP/1.1";

        final RequestLine requestLine= new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethodType.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void 지원하지않는_메소드면_예외발생() {

        String request = "PATCH /users HTTP/1.1";

        assertThatThrownBy(() -> new RequestLine(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
