package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Http Response 테스트")
class HttpResponseTest {

    @Test
    @DisplayName("Response: 200 OK 일 경우")
    void ok() {
        ResponseLine responseLine200 = ResponseLine.ok();
        HttpHeaders httpHeaders = HttpHeaders.init();
        ResponseBody responseBody = ResponseBody.empty();

        HttpResponse actual = new HttpResponse(responseLine200, httpHeaders, responseBody);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(actual.getHeaders().getHeaders()).isEmpty();
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
        assertThat(actual.getHeaders().getHeaders()).isEmpty();
        assertThat(actual.getBody().getContents()).isEmpty();
    }
}
