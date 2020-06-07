package view;

import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testutils.HttpRequestGenerator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("핸들바 엔진 테스트")
class HandlebarEngineTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarEngineTest.class);
    private static final HandlebarEngine HANDLEBAR_ENGINE = new HandlebarEngine();

    @Test
    @DisplayName("존재하지 않는 템플릿을 호출한 경우 예외 발생하며 404를 응답함")
    void drawFail() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /user/login HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();
        httpResponse.forward("do not exist");

        HANDLEBAR_ENGINE.draw(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("존재하는 템플릿을 호출한 경우")
    void draw() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /user/login HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();
        httpResponse.forward("/user/list");

        HANDLEBAR_ENGINE.draw(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getBodyLength()).isNotZero();
        logger.info(new String(httpResponse.getBody()));
    }

    @Test
    @DisplayName("존재하는 템플릿을 호출한 경우 - model이 필요없는 경우")
    void drawIndex() throws IOException {
        HttpRequest httpRequest = HttpRequestGenerator.init("GET /user/login HTTP/1.1");
        HttpResponse httpResponse = HttpResponse.init();
        httpResponse.forward("/index");

        HANDLEBAR_ENGINE.draw(httpRequest, httpResponse);

        assertThat(httpResponse.getStatusCode()).isEqualTo(StatusCode.OK);
        assertThat(httpResponse.getBodyLength()).isNotZero();
        logger.info(new String(httpResponse.getBody()));
    }
}
