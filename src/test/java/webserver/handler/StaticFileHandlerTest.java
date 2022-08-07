package webserver.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.StaticLocationProvider;
import webserver.http.*;

class StaticFileHandlerTest {

    private StaticFileHandler staticFileHandler;

    @BeforeEach
    void setup() {
        staticFileHandler = new StaticFileHandler(new StaticLocationProvider());
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
        HttpRequest httpRequest = createRequest(filePath);
        HttpResponse httpResponse = new HttpResponse();

        // when
        staticFileHandler.handle(httpRequest, httpResponse);

        // then
        Headers headers = httpResponse.getHeaders();
        String contentTypeHeader = headers.getValue("Content-Type");
        Assertions.assertThat(contentTypeHeader).isEqualTo(contentType);
    }

    private HttpRequest createRequest(String path) {
        RequestLine requestLine = RequestLine.parseOf("GET " + path + " HTTP/1.1");
        Headers headers = Headers.of(new Header("Accept", "*/*"));
        return new HttpRequest(requestLine, headers);
    }

}
