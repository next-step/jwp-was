package controller;

import controller.Controller;
import org.junit.jupiter.api.Test;
import webserver.HttpStatus;
import webserver.IndexController;
import webserver.Request;
import webserver.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void serving() throws IOException, URISyntaxException {
        Request request = new Request("GET /index.html HTTP/1.1");

        Controller controller = new IndexController();

        Response response = controller.serving(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getPath()).isEqualTo("/index.html");
        assertThat(response.getContentType()).isEqualTo("text/html;charset=utf-8");
    }

}