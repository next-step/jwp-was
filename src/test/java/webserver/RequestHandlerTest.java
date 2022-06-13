package webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    void index() throws IOException, URISyntaxException {
        RequestHandler requestHandler = new RequestHandler(null);

        Controller controller = requestHandler.match(new Request("GET /index.html HTTP/1.1"));

        Response response = controller.serving();

        assertThat(response.getPath()).isEqualTo("index.html");
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
    }

}