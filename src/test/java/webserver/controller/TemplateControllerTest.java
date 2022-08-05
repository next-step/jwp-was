package webserver.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.request.requestline.Version;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemplateControllerTest {
    private static Controller controller;

    @BeforeAll
    static void setUp() {
        controller = new TemplateController();
    }

    @Test
    @DisplayName("html 파일 요청 테스트")
    void htmlFileRequest() throws IOException, URISyntaxException {
        // given
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("template.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.OK)).isEqualTo(true),
                () -> assertThat(httpResponse.isHeaderValueEqual("Content-Type", "text/html;charset=utf-8")).isEqualTo(true)
        );
    }

    @ParameterizedTest
    @DisplayName("해당 요청에 대한 Mapping 이 일치하는지 확인한다.")
    @CsvSource(value = {
            "GET, /index.html, true",
            "GET, /index.htmls, false",
            "POST, /index.html, false",
            "POST, /index.htmls, false",
    })
    void isMatchRequest(Method method, String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path(path, new QueryString()), new Protocol("HTTP", Version.ONE_ONE)), new Header(), new QueryString());
        assertThat(controller.isMatchRequest(httpRequest)).isEqualTo(trueOrFalse);
    }
}