package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    void 리다이렉트_응답() {

        String location = "/";

        final HttpResponse redirect = HttpResponse.redirect(location);

        assertThat(redirect.getMessages().get(0)).isEqualTo("HTTP/1.1 302 OK \r\n");
    }
}
