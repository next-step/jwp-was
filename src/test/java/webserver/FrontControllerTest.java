package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.domain.HttpRequest;
import webserver.ui.FrontController;

import static org.assertj.core.api.Assertions.assertThat;

class FrontControllerTest {

    private static FrontController frontController;

    @BeforeAll
    static void staticSetUp() {
        frontController = new TestWebConfig().frontController();
    }


    @DisplayName("전달받은 requestLine이 유효한 경우 논리값 참을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html http/1.1"})
    void supportWithValidPath(String requestLine) {
        HttpRequest httpRequest = HttpRequest.newInstance(requestLine);

        assertThat(frontController.support(httpRequest.getRequestLine())).isTrue();

    }

    @DisplayName("전달받은 path가 유효하지 않은 경우 논리값 거짓을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"GET /invalidIndex.html http/1.1", "POST /index.html http/1.1"})
    void supportWithInValidPath(String requestLine) {
        HttpRequest httpRequest = HttpRequest.newInstance(requestLine);

        assertThat(frontController.support(httpRequest.getRequestLine())).isFalse();
    }

    /*
    @DisplayName("전달받은 path가 유효한 경우")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html"})
    void supportWithInValidPath(String path) {
        assertThat(frontController.support(path)).isFalse();
    }*/


}
