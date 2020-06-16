package http;

import http.controller.Controller;
import http.controller.PathController;
import http.controller.StaticController;
import http.request.RequestMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {
    @Test
    void pathControllerTest() {
        Controller controller = RequestMapper.getController("/index.html");
        assertThat(controller instanceof PathController).isTrue();
    }

    @Test
    void staticControllerTest() {
        Controller controller = RequestMapper.getController("/css/style.css");
        assertThat(controller instanceof StaticController).isTrue();
    }
}
