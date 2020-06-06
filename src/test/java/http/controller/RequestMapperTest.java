package http.controller;

import http.requests.HttpRequest;
import http.types.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    private HttpRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockRequest = new HttpRequest("GET /test-path HTTP/1.1", Collections.emptyList(), "");
    }

    @DisplayName("적절한 컨트롤러로 분기하는지 테스트")
    @Test
    void dispatch_request() {
        RequestMapper.addController("/test-path", HttpMethod.GET, new MockController());
        final Controller controller = RequestMapper.dispatch(mockRequest);
        assertThat(controller).isInstanceOf(MockController.class);
    }

    private class MockController implements Controller {
    }
}