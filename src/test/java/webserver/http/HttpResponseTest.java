package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HttpResponse 테스트")
class HttpResponseTest {

    private HttpResponse response;

    @BeforeEach
    void setUp() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        response = HttpResponse.of(dataOutputStream);
    }

    @DisplayName("forwardBody 테스트")
    @Test
    void forwardBody() {
        String contents = "contents";
        byte[] contentsBytes = contents.getBytes(StandardCharsets.UTF_8);

        response.forwardBody(contents);

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8"),
                        Map.entry(HttpHeaders.CONTENT_LENGTH, contentsBytes.length)),
                () -> assertThat(response.getBody()).isEqualTo(contentsBytes)
        );
    }

    @DisplayName("forward 테스트")
    @Test
    void forward() throws IOException, URISyntaxException {
        byte[] contentsBytes = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        response.forward("./templates/index.html", "/index.html");

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8"),
                        Map.entry(HttpHeaders.CONTENT_LENGTH, contentsBytes.length)),
                () -> assertThat(response.getBody()).isEqualTo(contentsBytes)
        );
    }

    @DisplayName("sendRedirect 테스트")
    @Test
    void sendRedirect() {
        response.sendRedirect("/index.html");

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/index.html")),
                () -> assertThat(response.getBody()).isEqualTo(new byte[0])
        );
    }

    @DisplayName("sendRedirect 테스트")
    @Test
    void sendRedirectWithCookie() {
        response.sendRedirectWithCookie("/user/login_failed.html", "logined=false; Path=/");

        assertAll(
                () -> assertThat(response.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(response.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/user/login_failed.html")),
                () -> assertThat(response.getBody()).isEqualTo(new byte[0])
        );
    }

}
