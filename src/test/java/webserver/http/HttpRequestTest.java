package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("Http Request 는 Request Line, Header, Body 로 이루어진다.")
    @Test
    void createTest() {
        // given
        RequestLine requestLine = RequestLine.parseOf("POST /user/create HTTP/1.1");

        Headers headers = Headers.parseOf(List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*"));

        RequestBody body = new RequestBody("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");

        // when
        HttpRequest httpRequest = new HttpRequest(requestLine, headers, body);

        // then
        assertThat(httpRequest.getRequestLine()).isEqualTo(requestLine);
        assertThat(httpRequest.getHeaders()).isEqualTo(headers);
        assertThat(httpRequest.getBody()).isEqualTo(body);
    }

    @DisplayName("InputStream 을 이용해 HttpRequest 를 생성할 수 있다.")
    static class CreateWithInputStreamTest {

        private static final String SRC_TEST_RESOURCES = "./src/test/resources";

        @Test
        void createGetRequestTest() throws Exception {
            // given
            InputStream in = new FileInputStream(SRC_TEST_RESOURCES + "/http_GET.txt");

            // when
            HttpRequest httpRequest = HttpRequest.create(in);

            // then
            assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
            assertThat(httpRequest.getPath()).isEqualTo("/user/create");
            assertThat(httpRequest.getHeaders().getValue("Connection")).isEqualTo("keep-alive");
            assertThat(httpRequest.getParameter("name")).isEqualTo("JaeSung");
            assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        }

        @Test
        void createPostRequestTest() throws Exception {
            // given
            InputStream in = new FileInputStream(SRC_TEST_RESOURCES + "/http_POST.txt");

            // when
            HttpRequest httpRequest = HttpRequest.create(in);

            // then
            assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
            assertThat(httpRequest.getPath()).isEqualTo("/user/create");
            assertThat(httpRequest.getHeaders().getValue("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
            assertThat(httpRequest.getBody("userId")).isEqualTo("javajigi");
            assertThat(httpRequest.getBody("name")).isEqualTo("JaeSung");
        }
    }
}
