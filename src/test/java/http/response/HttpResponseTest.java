package http.response;

import http.common.ContentType;
import http.common.HeaderFieldName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
    @DisplayName("response200 메서드 호출 시 상태코드는 302가 된다")
    void response200HtmlStatusCodeTest() {
        final StatusCode statusCode = StatusCode.OK;
        final int expected = statusCode.getCode();
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.setContentType(ContentType.TEXT_CSS_UTF_8);
        httpResponse.setBody(body);
        int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response200 메서드 호출 시 정상적인 Content-Type 헤더가 추가된다")
    void response200HtmlContentTypeTest() {
        final HeaderFieldName contentTypeName = HeaderFieldName.CONTENT_TYPE;
        final ContentType contentTypeValue = ContentType.TEXT_HTML_UTF_8;
        final String expected = contentTypeValue.getValue();
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.setContentType(contentTypeValue);
        httpResponse.setBody(body);
        String result = httpResponse.getHeader(contentTypeName);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response200HTML 메서드 호출 시 정상적인 Content-Length 헤더가 추가된다")
    void response200HtmlContentLengthTest() {
        final HeaderFieldName contentLength = HeaderFieldName.CONTENT_LENGTH;
        String html = "<html><body>test입니다.</body></html>";
        final byte[] body = html.getBytes();
        final String expected = String.valueOf(body.length);
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.setContentType(ContentType.TEXT_HTML_UTF_8);
        httpResponse.setBody(body);
        String result = httpResponse.getHeader(contentLength);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response302 메서드 호출 시 상태코드는 302 된다")
    void response302StatusCodeTest() {
        final StatusCode statusCode = StatusCode.FOUND;
        final int expected = statusCode.getCode();
        final HttpResponse httpResponse = new HttpResponse();
        final String locationUrl = "http://www.google.com";

        httpResponse.sendRedirect(locationUrl);
        int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response302 메서드 호출 시 입력받은 url을 가지는 Location 헤더가 추가된다")
    void response302LocationTest() {
        final HttpResponse httpResponse = new HttpResponse();
        final String locationUrl = "http://www.google.com";

        httpResponse.sendRedirect(locationUrl);
        String result = httpResponse.getHeader(HeaderFieldName.LOCATION);

        assertThat(result).isEqualTo(locationUrl);
    }

}