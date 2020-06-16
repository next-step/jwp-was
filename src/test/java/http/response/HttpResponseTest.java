package http.response;

import http.common.HeaderFieldName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    @DisplayName("HttpResponse 객체를 생성할 수 있다")
    void createHttpResponse() {
        new HttpResponse();
    }

    @Test
    @DisplayName("HttpResponse 객체를 생성 시 상태코드는 200이다")
    void createHttpResponse200() {
        final HttpResponse httpResponse = new HttpResponse();
        final int expected = 200;

        final int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("HttpResponse 객체에 쿠키를 저장할 수 있다")
    void addCookieTest() {
        final HttpResponse httpResponse = new HttpResponse();
        final String cookieName = "logined";
        final String cookieValue = "true";

        httpResponse.addCookie(cookieName, cookieValue);
    }

    @Test
    @DisplayName("sendRedirect 메서드 호출 시 상태코드는 302 된다")
    void response302StatusCodeTest() {
        final StatusCode statusCode = StatusCode.FOUND;
        final int expected = statusCode.getCode();
        final HttpResponse httpResponse = new HttpResponse();
        final String locationUrl = "http://www.google.com";

        httpResponse.sendRedirect(locationUrl);
        final int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("sendRedirect 메서드 호출 시 입력받은 url을 가지는 Location 헤더가 추가된다")
    void response302LocationTest() {
        final HttpResponse httpResponse = new HttpResponse();
        final String locationUrl = "http://www.google.com";

        httpResponse.sendRedirect(locationUrl);
        final Optional<String> result = httpResponse.getHeader(HeaderFieldName.LOCATION);

        assertThat(result).hasValue(locationUrl);
    }

}