package webserver;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void serving() throws IOException, URISyntaxException {
        Request request = new Request("GET /index.html HTTP/1.1");

        Controller controller = new IndexController();

        Response response = controller.serving(request);

        assertThat(response.getPath()).isEqualTo("index.html");
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
    }

}