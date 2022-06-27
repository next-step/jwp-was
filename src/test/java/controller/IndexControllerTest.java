package controller;

import org.junit.jupiter.api.Test;
import webserver.*;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void serving() throws IOException, URISyntaxException {
        Request request = new Request(new RequestLine("GET /index.html HTTP/1.1"), null, null);

        Response response = DispatcherServlet.match(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getPath()).isEqualTo("/index.html");
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
    }

}