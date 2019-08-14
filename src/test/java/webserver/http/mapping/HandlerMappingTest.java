package webserver.http.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.controller.*;
import webserver.http.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerMappingTest {

    private HandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new HandlerMapping();
    }

    @DisplayName("request 별 올바른 controller를 가져오는지 확인")
    @ParameterizedTest
    @MethodSource("handlerMappingTestCase")
    void name(String requestStr, Class<Controller> expectedController) throws IOException {

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(requestStr.getBytes()));
        Controller controller = handlerMapping.getController(request);

        assertThat(controller).isInstanceOf(expectedController);
    }

    private static Stream<Arguments> handlerMappingTestCase() {

        String mainControllerRequest = "GET /index HTTP/1.1";
        String loginControllerRequest = "GET /user/login HTTP/1.1";
        String createUserControllerRequest = "POST /user/create HTTP/1.1";
        String registerFormControllerRequest = "GET /user/form HTTP/1.1";
        String userListControllerRequest = "GET /user/list HTTP/1.1";

        return Stream.of(
                Arguments.of(mainControllerRequest, MainController.class),
                Arguments.of(loginControllerRequest, LoginController.class),
                Arguments.of(createUserControllerRequest, CreateUserController.class),
                Arguments.of(registerFormControllerRequest, RegisterFormController.class),
                Arguments.of(userListControllerRequest, UserListController.class)
        );
    }
}