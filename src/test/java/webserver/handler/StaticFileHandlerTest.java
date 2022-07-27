package webserver.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.*;

class StaticFileHandlerTest {

    private StaticFileHandler staticFileHandler;

    @BeforeEach
    void setup() {
        staticFileHandler = new StaticFileHandler();
    }

    @DisplayName("request line 의 path 가 확장자가 있으면 Resource 요청이다.")
    @Test
    void supportTest() {
        // given
        Request request = createRequest("/index.html");

        // when
        boolean support = staticFileHandler.isSupport(request);

        // then
        Assertions.assertThat(support).isTrue();
    }

    @DisplayName("파일 확장자에 따라서 Response 의 Content-Type 헤더가 다르다")
    @CsvSource(value = {
            "/index.html -> text/html",
            "/css/styles.css -> text/css",
            "/js/scripts.js -> application/javascript"
    }, delimiterString = " -> ")
    @ParameterizedTest
    void contentTypeTest(String filePath, String contentType) {
        // given
        Request request = createRequest(filePath);

        // when
        Response handle = staticFileHandler.handle(request);

        // then
        Headers headers = handle.getHeaders();
        String contentTypeHeader = headers.getValue("Content-Type");
        Assertions.assertThat(contentTypeHeader).isEqualTo(contentType);
    }

    private Request createRequest(String path) {
        RequestLine requestLine = RequestLine.parseOf("GET " + path + " HTTP/1.1");
        Headers headers = Headers.of(new Header("Accept", "*/*"));
        return new Request(requestLine, headers);
    }

}