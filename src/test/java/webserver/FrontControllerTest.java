package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;
import webserver.domain.HttpRequest;
import webserver.domain.Response;
import webserver.ui.FrontController;

import java.io.IOException;
import java.net.URISyntaxException;

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

    @DisplayName("요청 request path가 유효한 html요청일경우 해당 html 파일을 찾아 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"GET /index.html http/1.1"})
    void executeWithInValidPath(String requestLine) throws IOException, URISyntaxException {
        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        String expectedBody = new String(bytes);
        HttpRequest httpRequest = HttpRequest.newInstance(requestLine);

        Response response = frontController.execute(httpRequest);

        assertThat(response.getBody()).isEqualTo(expectedBody);
    }


}
