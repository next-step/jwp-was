package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.controller.LoginController;
import webserver.http.controller.NotFoundController;
import webserver.http.controller.ResourceController;
import webserver.http.controller.UserCreateController;
import webserver.http.controller.UserListController;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HandlerMappingTest {

    @DisplayName("정의된 EndPoint에 해당하는 Controller 를 반환한다.")
    @Test
    void handlerMapping() {
        HandlerMapping handlerMapping = new HandlerMapping();

        assertAll(
                () -> HandlerMapping.handlers.keySet()
                                            .forEach(key -> assertThat(handlerMapping.getHandler(key)).isEqualTo(HandlerMapping.handlers.get(key)))
        );
    }


    static Stream<Arguments> handlers() {
        return Stream.of(
                Arguments.arguments("/user/login", LoginController.class),
                Arguments.arguments("/user/create", UserCreateController.class),
                Arguments.arguments("/user/list", UserListController.class)
        );
    }

    @DisplayName("Resource 요청이 들어오면 ResorceController를 반환한다.")
    @ValueSource(strings = {"/index.html", "/favicon.ico", "/css/styles.css", "/js/scripts.js"})
    @ParameterizedTest
    void resourceHandler(String path) {
        HandlerMapping handlerMapping = new HandlerMapping();

        assertThat(handlerMapping.getHandler(path)).isInstanceOf(ResourceController.class);
    }

    @DisplayName("정의되지 않은 요청이 들어오면 NotFoundController를 반환한다.")
    @ValueSource(strings = {"/index/test", "/user"})
    @ParameterizedTest
    void notFoundHandler(String path) {
        HandlerMapping handlerMapping = new HandlerMapping();

        assertThat(handlerMapping.getHandler(path)).isInstanceOf(NotFoundController.class);
    }
}
