package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;
import webserver.http.MediaType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http Response 테스트")
class HttpResponseTest {

    private static final String VIEW_RESOURCES_PATH = "./templates";

    @Test
    @DisplayName("Response: 200 OK 일 경우")
    void ok() {
        ResponseLine responseLine200 = ResponseLine.ok();
        HttpHeaders httpHeaders = HttpHeaders.init();
        ResponseBody responseBody = ResponseBody.empty();

        HttpResponse actual = new HttpResponse(responseLine200, httpHeaders, responseBody);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(actual.getBody().getContents()).isEmpty();
    }

    @Test
    @DisplayName("Response: 302 Found (Redirect) 일 경우")
    void redirect() {
        ResponseLine responseLint302 = ResponseLine.redirect();
        HttpHeaders httpHeaders = HttpHeaders.redirect("/index.html");
        ResponseBody responseBody = ResponseBody.empty();

        HttpResponse actual = new HttpResponse(responseLint302, httpHeaders, responseBody);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(actual.getHeaders().hasLocation()).isTrue();
        assertThat(actual.getBody().getContents()).isEmpty();
    }

    @Test
    @DisplayName("Response: 허용하지 않는 메서드일 경우")
    void methodNotSupported() {
        ResponseLine responseLine405 = new ResponseLine(HttpStatus.METHOD_NOT_ALLOWED);
        HttpHeaders httpHeaders = HttpHeaders.init();
        ResponseBody responseBody = ResponseBody.empty();

        HttpResponse actual = new HttpResponse(responseLine405, httpHeaders, responseBody);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 405 Method Not Allowed");
        assertThat(actual.getBody().getContents()).isEmpty();
    }

    @Test
    @DisplayName("정적 리소스를 읽어 응답으로 보내는 forward 응답 Body 에는 index.html 이 포함되어 있어야 한다.")
    void responseForward() throws URISyntaxException, IOException {
        MediaType mediaType = MediaType.HTML;
        byte[] index = getPageToBytes();

        HttpResponse httpResponse = HttpResponse.forward(mediaType, VIEW_RESOURCES_PATH, "/index.html");

        assertThat(httpResponse.getBody().getContentsLength()).isEqualTo(6988);
        assertThat(httpResponse.getBody().getContents()).isEqualTo(index);
    }

    @Test
    @DisplayName("redirect 응답 시 Location Header 정보가 /index.html 로 포함되어 있어야 한다.")
    void responseRedirect() {
        HttpHeaders httpHeaders = HttpHeaders.redirect("/index.html");

        HttpResponse httpResponse = HttpResponse.redirect(httpHeaders);

        assertThat(httpResponse.getHeaders().getHeader(HttpHeader.LOCATION)).contains("/index.html");
    }

    @Test
    @DisplayName("redirect 응답 시 Header 에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.")
    void responseCookies() {
        HttpHeaders httpHeaders = HttpHeaders.redirect("/index.html");
        httpHeaders.addResponseHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");

        HttpResponse httpResponse = HttpResponse.redirect(httpHeaders);

        assertThat(httpResponse.getHeaders().getHeader(HttpHeader.LOCATION)).contains("/index.html");
        assertThat(httpResponse.getHeaders().getHeader(HttpHeader.SET_COOKIE)).contains("logined=true");
    }

    private byte[] getPageToBytes() throws IOException, URISyntaxException {
        Path path = Paths.get(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource(VIEW_RESOURCES_PATH + "/index.html")).toURI()
        );
        return Files.readAllBytes(path);
    }
}
