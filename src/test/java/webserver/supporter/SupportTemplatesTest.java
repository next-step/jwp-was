package webserver.supporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

class SupportTemplatesTest {

    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @DisplayName("지원하는 경로의 리소스파일은 찾아서 반환된다. (Ok) ")
    @ParameterizedTest
    @CsvSource(value = {"GET /qna/form.html HTTP/1.1", "GET /qna/show.html HTTP/1.1",
        "GET /user/form.html HTTP/1.1", "GET /user/profile.html HTTP/1.1", "GET /index.html HTTP/1.1"})
    void isSupportedTest(String startLine) {
        HttpRequest httpRequest = new HttpRequest(startLine);
        HttpResponse httpResponse = new HttpResponse();
        SupportTemplates.execute(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("지원하지 않는 경로의 리소스파일은 찾을 수 없다 (Not Found)")
    @ParameterizedTest
    @CsvSource(value = {"GET /qna/form HTTP/1.1", "GET /show.html HTTP/1.1",
        "GET /user/.html HTTP/1.1", "GET /user/profile1.html HTTP/1.1", "GET /1/index.html HTTP/1.1"})
    void notSupportedTest(String invalidStartLine) {
        HttpRequest httpRequest = new HttpRequest(invalidStartLine);
        HttpResponse httpResponse = new HttpResponse();
        SupportTemplates.execute(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
