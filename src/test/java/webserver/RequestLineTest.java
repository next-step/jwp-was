package webserver;

import model.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineTest {

    @Test
    void GET_요청을_파싱한다() {
        String request = "GET /users HTTP/1.1";

        final RequestLine requestLine= new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getFullRequestPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void POST_요청을_파싱한다() {
        String request = "POST /users HTTP/1.1";

        final RequestLine requestLine= new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getFullRequestPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void 지원하지않는_메소드면_예외발생() {
        String request = "PATCH /users HTTP/1.1";

        assertThatThrownBy(() -> new RequestLine(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿼리스트링_파싱기능() {
        final String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getRequestParams().getOneValue("userId")).isEqualTo("javajigi");
    }
}
