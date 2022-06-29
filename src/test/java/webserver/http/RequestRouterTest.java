package webserver.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.controller.AbstractController;
import webserver.http.controller.Controller;
import webserver.http.controller.NotFoundController;
import webserver.http.controller.ResourceController;
import webserver.http.request.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestRouterTest {
    @Test
    void 요청_경로에_일치하는_Controller가_등록된_경우_해당_Controller_리턴() {
        // given
        final RequestRouter requestRouter = new RequestRouter();
        final AbstractController aController = new AbstractController() {
        };
        requestRouter.add("/a", aController);

        final HttpRequest request = mock(HttpRequest.class);
        when(request.getPath()).thenReturn("/a");

        // when
        final Controller routedController = requestRouter.getRoutedController(request);

        // then
        assertThat(routedController).isSameAs(aController);
    }

    @Test
    void 요청_경로에_일치하는_Controller가_등록되지_않는_경우_NotFoundController_리턴() {
        // given
        final RequestRouter requestRouter = new RequestRouter();
        requestRouter.add("/a", new AbstractController() {
        });

        final HttpRequest request = mock(HttpRequest.class);
        when(request.getPath()).thenReturn("/b");

        // when
        final Controller routedController = requestRouter.getRoutedController(request);

        // then
        assertThat(routedController).isInstanceOf(NotFoundController.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/test.html", // html
            "/css/test.css", // css
            "/js/test.js", // javascript
            "/favicon.ico", // favicon
            "/fonts/glyphicons-halflings-regular.ttf" // font
    })
    void 리소스_요청인_경우_ResourceController를_리턴(final String requestPath) {
        // given
        final RequestRouter requestRouter = new RequestRouter();

        final HttpRequest request = mock(HttpRequest.class);
        when(request.getPath()).thenReturn(requestPath);

        // when
        final Controller routedController = requestRouter.getRoutedController(request);

        // then
        assertThat(routedController).isInstanceOf(ResourceController.class);
    }
}
