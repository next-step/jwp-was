package webserver.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.header.type.EntityHeader;
import webserver.http.header.Header;
import webserver.http.header.HeaderValue;
import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.statusline.StatusCode;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class StaticControllerTest {
    private static Controller controller;

    @BeforeAll
    static void setUp() {
        controller = new StaticController();
    }

    @Test
    @DisplayName("static 파일 요청 테스트")
    void staticFileRequest() throws IOException, URISyntaxException {
        // given
        HttpRequest httpRequest = RequestTestUtil.readTestRequest("static.txt");

        // when
        HttpResponse httpResponse = controller.process(httpRequest);

        // then
        assertAll(
                () -> assertThat(httpResponse.isStatusCodeEqual(StatusCode.OK)).isTrue(),
                () -> assertThat(httpResponse.isHeaderValueEqual(EntityHeader.CONTENT_TYPE, HeaderValue.TEXT_CSS_UTF8)).isTrue()
        );
    }

    @ParameterizedTest
    @DisplayName("GET 요청 이외의 메서드는 Not Found 를 반환한다.")
    @CsvSource(value = {
            "POST, PUT, DELETE, PATCH"
    })
    void throw_exception_exceptGetMethod(Method method) throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(method, new Path("/css/styles.css", new QueryString()), Protocol.ofHttp_V1_1()), new Header(), new QueryString());
        assertThat(controller.process(httpRequest)).isEqualTo(HttpResponse.notFound());
    }

    @ParameterizedTest
    @DisplayName("요청에 대한 Path 가 일치하는지 확인한다.")
    @CsvSource(value = {
            "/style.css, true",
            "/style.csss, false",
    })
    void isMatchRequest(String path, boolean trueOrFalse) {
        HttpRequest httpRequest = new HttpRequest(new RequestLine(Method.GET, new Path(path, new QueryString()), Protocol.ofHttp_V1_1()), new Header(), new QueryString());
        assertThat(controller.isMatchPath(httpRequest)).isEqualTo(trueOrFalse);
    }
}