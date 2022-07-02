package webserver;

import http.HttpRequest;
import http.MediaType;
import http.RequestLine;
import http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestControllerContainerTest {

    @Test
    void match() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new RequestLine("GET /index.html HTTP/1.1"), null, null);

        HttpResponse httpResponse = RequestControllerContainer.match(httpRequest);

        assertThat(httpResponse.getPath()).isEqualTo("/index.html");
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
    }

}