package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.DispatcherController;
import webserver.http.stub.StubHttpRequest;
import webserver.http.stub.StubHttpResponse;
import webserver.controller.Controller;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpControllerHandlerTest {

    private static String EXPECTED_RESULT = "result";

    @Test
    void controllerHandler() {
        DispatcherController dispatcherController = new DispatcherController();
        dispatcherController.setMappingRegistry(getMockController());
        HttpRequest mockHttpRequest = new StubHttpRequest("/test");
        HttpResponse mockHttpResponse = new StubHttpResponse();
        dispatcherController.dispatch(mockHttpRequest, mockHttpResponse);
        assertThat(mockHttpResponse.getCookie("dummy")).isEqualTo(EXPECTED_RESULT);
    }

    private static Map<String, Controller> getMockController() {
        return new HashMap<String, Controller>(){{
            put("/test", new MockController());
        }};
    }

    private static class MockController implements Controller {
        @Override
        public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
            httpResponse.addCookie("dummy", EXPECTED_RESULT);
        }
    }


}
