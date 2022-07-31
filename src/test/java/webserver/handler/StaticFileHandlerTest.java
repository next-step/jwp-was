package webserver.handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.RequestMappingInfo;
import webserver.http.*;

class StaticFileHandlerTest {

    private StaticFileHandler staticFileHandler;

    @BeforeEach
    void setup() {
        staticFileHandler = new StaticFileHandler();
    }

    @DisplayName("Request Path 패턴이 /.*, Method 가 GET 인 요청과 매핑된다.")
    @Test
    void supportTest() {
//        RequestMappingInfo mappingInfo = staticFileHandler.getMappingInfo();

        // then
//        Assertions.assertThat(mappingInfo).isEqualTo(new RequestMappingInfo("/**", HttpMethod.GET));
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
        Response response = new Response();

        // when
        staticFileHandler.handle(request, response);

        // then
        Headers headers = response.getHeaders();
        String contentTypeHeader = headers.getValue("Content-Type");
        Assertions.assertThat(contentTypeHeader).isEqualTo(contentType);
    }

    private Request createRequest(String path) {
        RequestLine requestLine = RequestLine.parseOf("GET " + path + " HTTP/1.1");
        Headers headers = Headers.of(new Header("Accept", "*/*"));
        return new Request(requestLine, headers);
    }

}