package webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {

    @Test
    void index() throws IOException, URISyntaxException {
        RequestHandler requestHandler = new RequestHandler(null);

        Request request = new Request("GET /index.html HTTP/1.1");

        Controller controller = requestHandler.match(request.getRequestLine().getPath());
        Response response = controller.serving(request);

        assertThat(response.getPath()).isEqualTo("index.html");
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
    }

}