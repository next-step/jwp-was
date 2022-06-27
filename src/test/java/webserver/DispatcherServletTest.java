package webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {

    @Test
    void match() throws IOException, URISyntaxException {
        Request request = new Request(new RequestLine("GET /index.html HTTP/1.1"), null, null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getPath()).isEqualTo("/index.html");
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
    }

}