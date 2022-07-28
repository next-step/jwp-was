package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.StatusCode.NOT_FOUND;
import static webserver.response.StatusCode.OK;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.ContentType;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;
import webserver.response.StatusLine;

class DefaultControllerTest {

    private final DefaultController controller = new DefaultController();

    @DisplayName("정적 파일을 찾아 응답 객체를 반환한다")
    @Test
    void static_file_ok_response() throws IOException {
        // given
        String httpRequestMessage = "GET /index.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        String responseBody = "<html><body>Hello world!</body></html>\n";

        final ResponseHeaders headers = new ResponseHeaders();
        headers.add("Content-Type", ContentType.HTML.getMediaType());
        headers.add("Content-Length", String.valueOf(responseBody.getBytes().length));
        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, OK), headers, new ResponseBody(responseBody));

        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("정적 파일을 찾지 못한 응답 객체를 반환한다")
    @Test
    void static_file_not_found_response() throws IOException {
        // given
        String httpRequestMessage = "GET /home.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, NOT_FOUND), new ResponseHeaders(), ResponseBody.EMPTY_RESPONSE_BODY);

        assertThat(actual).isEqualTo(expected);

    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
