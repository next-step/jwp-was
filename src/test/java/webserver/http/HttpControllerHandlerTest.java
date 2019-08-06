package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.mock.MockHttpRequest;
import webserver.http.mock.MockHttpResponse;
import webserver.servlet.Controller;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpControllerHandlerTest {

    private static String EXPECTED_RESULT = "result";

    @Test
    void controllerHandler() {
        HttpControllerHandler httpControllerHandler = new HttpControllerHandler(getMockController());
        HttpRequest mockHttpRequest = new MockHttpRequest("/test");
        HttpResponse mockHttpResponse = new MockHttpResponse();
        httpControllerHandler.handle(mockHttpRequest, mockHttpResponse);
        assertThat(mockHttpResponse.getCookies().get("dummy")).isEqualTo(EXPECTED_RESULT);
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
