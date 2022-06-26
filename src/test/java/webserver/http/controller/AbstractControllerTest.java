package webserver.http.controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AbstractControllerTest {

    @Test
    void GET_요청에_대해_doGet_메서드를_실행한다() throws IOException {
        // given
        final HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.GET);

        final HttpResponse response = mock(HttpResponse.class);

        // when
        final TestController controller = new TestController();
        controller.service(request, response);

        // then
        assertThat(controller.doGetCalled).isTrue();
        assertThat(controller.doPostCalled).isFalse();
    }

    @Test
    void POST_요청에_대해_doPost_메서드를_실행한다() throws IOException {
        // given
        final HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.POST);

        final HttpResponse response = mock(HttpResponse.class);

        // when
        final TestController controller = new TestController();
        controller.service(request, response);

        // then
        assertThat(controller.doGetCalled).isFalse();
        assertThat(controller.doPostCalled).isTrue();
    }

    @Test
    void HTTP_메서드_핸들링_설정을_안한_경우() throws IOException {
        // given
        final HttpRequest request = mock(HttpRequest.class);
        when(request.getMethod()).thenReturn(HttpMethod.POST);

        final HttpResponse response = mock(HttpResponse.class);

        // when
        final AbstractController controller = new AbstractController() {
        };

        // then
        controller.service(request, response);
        verify(response).responseMethodNotAllowed();
    }

    private static class TestController extends AbstractController {
        private boolean doGetCalled = false;
        private boolean doPostCalled = false;

        @Override
        protected void doGet(final HttpRequest request, final HttpResponse response) {
            this.doGetCalled = true;
        }

        @Override
        protected void doPost(final HttpRequest request, final HttpResponse response) {
            this.doPostCalled = true;
        }
    }
}
