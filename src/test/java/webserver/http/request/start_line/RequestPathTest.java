package webserver.http.request.start_line;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestPathTest {

    @Test
    void isStaticContentType() {
        RequestPath requestPath = new RequestPath("GET /index.html HTTP/1.1");

        assertThat(requestPath.isStaticContentType()).isTrue();
    }
}
