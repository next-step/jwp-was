package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private static final String RESPONSE_TEST_DIR = "./src/test/resources";

    @DisplayName("응답 객체에 쿠키를 추가할 수 있다.")
    @Test
    void addCookieTest() {
        // given
        Cookie loginCookie = new Cookie("logined", "true", "/");
        HttpResponse httpResponse = new HttpResponse();

        // when
        httpResponse.addCookie(loginCookie);

        // then
        assertThat(httpResponse.getHeaders().getValue("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @DisplayName("HttpResponse 가 Redirect 됐을때 올바른 Http 응답을 만들 수 있다.")
    @Test
    void createWithOutputStream() throws Exception {
        // given
        String redirectFile = RESPONSE_TEST_DIR + "/HTTP_REDIRECT.txt";
        OutputStream outputStream = new FileOutputStream(redirectFile);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        // when
        httpResponse.sendRedirect("/index.html");

        // then
        String expectedResponse =
                "HTTP/1.1 302 FOUND\n" +
                        "Location: /index.html\n\n";

        assertThat(new File(redirectFile)).hasContent(expectedResponse);
    }

    @DisplayName("HttpResponse 가 sendError 됐을때 올바른 Http 응답을 만들 수 있다.")
    @Test
    void createSendErrorTest() throws Exception {
        // given
        String sendErrorFile = RESPONSE_TEST_DIR + "/HTTP_SEND_ERROR.txt";
        OutputStream outputStream = new FileOutputStream(sendErrorFile);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        // when
        httpResponse.sendError(Status.NOT_FOUND, "not found resource");

        // then
        String expectedResponse =
                "HTTP/1.1 404 NOT_FOUND\n" +
                        "Content-Length: 18\n\n" +
                        "not found resource";

        assertThat(new File(sendErrorFile)).hasContent(expectedResponse);
    }
}
