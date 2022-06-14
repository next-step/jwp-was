package webserver.request;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.Protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestTest {

    @DisplayName("Request 의 line, header, body 정보를 조회할 수 있다.")
    @Test
    void of() {
        RequestLine requestLine = RequestLine.from(
                "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1"
        );
        RequestHeader requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
                "Content-Length: 64",
                "Cookie: loggedIn=true"
        ));
        RequestBody requestBody = RequestBody.from(
                "userId=pyro&password=pw&name=Gho"
        );
        Request request = new Request(requestLine, requestHeader, requestBody);

        assertAll(
                () -> assertThat(request.getMethod()).isEqualTo(RequestMethod.GET),
                () -> assertThat(request.getPath()).isEqualTo("/users"),
                () -> assertThat(request.getQuery("userId")).isEqualTo("javajigi"),
                () -> assertThat(request.getQuery("password")).isEqualTo("password"),
                () -> assertThat(request.getQuery("name")).isEqualTo("JaeSung"),
                () -> assertThat(request.getProtocol()).isEqualTo(Protocol.HTTP_1_1),

                () -> assertThat(request.getContentType()).isEqualTo("text/html"),
                () -> assertThat(request.getContentLength()).isEqualTo(64),
                () -> assertThat(request.getCookie()).isEqualTo("loggedIn=true"),

                () -> assertThat(request.getBody("userId")).isEqualTo("pyro"),
                () -> assertThat(request.getBody("password")).isEqualTo("pw"),
                () -> assertThat(request.getBody("name")).isEqualTo("Gho")
        );
    }
}
