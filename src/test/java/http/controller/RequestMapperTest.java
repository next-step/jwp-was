package http.controller;

import http.requests.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    private HttpRequest request;

    @DisplayName("적절한 컨트롤러로 분기하는지 테스트")
    @Test
    void dispatch_request() {
        final Controller controller = RequestMapper.dispatch(request);
        assertThat(controller).isInstanceOf(TestController.class);
    }
}