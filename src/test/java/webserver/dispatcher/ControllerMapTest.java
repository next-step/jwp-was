package webserver.dispatcher;

import http.controller.Controller;
import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.types.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapTest {

    @DisplayName("컨트롤러 맵이 제대로 컨트롤러를 매핑하는지 테스트")
    @Test
    void get_controller() throws Exception {
        final RequestBranch branch = new RequestBranch("/mock-test", HttpMethod.GET);
        final HashMap<RequestBranch, Controller> map = new HashMap<>();
        map.put(branch, new MockController());
        final ControllerMap controllerMap = new ControllerMap(map);

        final String testRequest = "GET /mock-test HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n";

        try (final InputStream input = new ByteArrayInputStream(testRequest.getBytes())) {
            final HttpRequest httpRequest = new HttpRequest(input);
            assertThat(controllerMap.getController(httpRequest)).isInstanceOf(MockController.class);
        }
    }

    private static class MockController implements Controller {

        @Override
        public void service(HttpRequest request, HttpResponse response) {
            // no-op
        }
    }
}