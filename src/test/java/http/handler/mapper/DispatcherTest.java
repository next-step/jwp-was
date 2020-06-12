package http.handler.mapper;

import http.common.HttpStatus;
import http.handler.ExceptionHandler;
import http.handler.Handler;
import http.handler.StaticResourceHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherTest {

    @ParameterizedTest
    @NullAndEmptySource
    void getHandlerForNullAndEmpty(String url) {
        Handler handler = Dispatcher.getHandler(url);

        assertThat(handler).isNotNull();
        assertThat(handler.getClass()).isEqualTo(ExceptionHandler.class);
        assertThat(handler.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(handler.getContentType()).isEqualTo(StaticResource.HTML.getContentType());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "/js/jquery-2.2.0.min.js",
        "/js/bootstrap.min.js",
        "/js/scripts.js",
        "/css/bootstrap.min.css",
        "/css/styles.css",
        "/fonts/glyphicons-halflings-regular.woff",
        "/fonts/glyphicons-halflings-regular.woff2",
        "/fonts/glyphicons-halflings-regular.ttf",
        "/user/form.html",
        "/user/login.html",
        "/user/login_failed.html",
    })
    void getHandlerForStaticResources(String url) {
        Handler handler = Dispatcher.getHandler(url);

        assertThat(handler).isNotNull();
        assertThat(handler.getClass()).isEqualTo(StaticResourceHandler.class);
        assertThat(handler.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(handler.getPath()).isEqualTo(url);
    }

    @Test
    void getHandlerForUserHandler() {
        for (Map.Entry<String, Handler> mapEntry : Dispatcher.getHandlers().entrySet()) {
            Handler handler = Dispatcher.getHandler(mapEntry.getKey());

            assertThat(handler).isEqualTo(mapEntry.getValue());
        }
    }
}