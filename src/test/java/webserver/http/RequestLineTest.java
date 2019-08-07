package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.request.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    public static final RequestLine GET = RequestLine.parse("GET /users HTTP/1.1");
    public static final RequestLine GET_QUERYSTRING = RequestLine.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
    public static final RequestLine POST = RequestLine.parse("POST /users HTTP/1.1");

    @Test
    void get_parse() {
        assertThat(GET.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(GET.getPath()).isEqualTo("/users");
        assertThat(GET.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void get_with_querystring_parse2() {
        assertThat(GET_QUERYSTRING.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(GET_QUERYSTRING.getPath()).isEqualTo("/users");
        assertThat(GET_QUERYSTRING.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(GET_QUERYSTRING.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @Test
    void post_parse() {
        assertThat(POST.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(POST.getPath()).isEqualTo("/users");
        assertThat(POST.getVersion()).isEqualTo("HTTP/1.1");
    }
}
