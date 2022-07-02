package webserver;

import controller.IndexMappingController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.MediaType;
import webserver.http.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestControllerContainerTest {

    @Test
    void match() throws Exception {
        HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /index.html HTTP/1.1"), null, null);

        HttpResponse httpResponse = new IndexMappingController().service(httpRequest);

        assertThat(httpResponse.getPath()).isEqualTo("/index.html");
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
    }

}