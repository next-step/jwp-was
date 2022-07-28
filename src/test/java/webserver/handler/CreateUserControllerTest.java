package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.FOUND;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeaders;
import webserver.response.StatusLine;

class CreateUserControllerTest {

    private final CreateUserController controller = new CreateUserController();

    @DisplayName("회원 가입 후 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_create_user_return_redirect_response() throws IOException {
        // given
        String httpRequestMessage = "POST /user/create HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 34\r\n"
            + "\r\n"
            + "userId=admin&email=admin@email.com\n\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        final HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        final HttpResponse actual = controller.handle(httpRequest);

        //then
        final ResponseHeaders headers = new ResponseHeaders();
        headers.add("Location", "/index.html");
        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, FOUND), headers, EMPTY_RESPONSE_BODY);

        assertThat(actual).isEqualTo(expected);

    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
