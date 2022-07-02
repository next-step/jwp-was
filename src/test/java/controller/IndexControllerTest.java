package controller;

import org.junit.jupiter.api.Test;
import webserver.http.*;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void serving() throws Exception {
        HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /index.html HTTP/1.1"), null, null);

        HttpResponse httpResponse = new IndexMappingController().service(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getPath()).isEqualTo("/index.html");
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
    }

}