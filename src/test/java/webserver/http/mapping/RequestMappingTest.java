package webserver.http.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.controller.Controller;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {
    @DisplayName("support, getController check")
    @ParameterizedTest
    @ValueSource(strings = {"/", "/user/create", "/user/login", "/user/list.html"})
    void handle(String requestUri) {
        boolean support = RequestMapping.support(requestUri);
        Controller controller = RequestMapping.getController(requestUri);

        assertThat(support).isTrue();
        assertThat(controller instanceof Controller).isTrue();
    }
}