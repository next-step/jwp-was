package http.response;

import http.common.HeaderFieldName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void createHttpResponse() {
        new HttpResponse();
    }

    @Test
    void addCookieTest() {
        final HttpResponse httpResponse = new HttpResponse();
        final String cookieName = "logined";
        final String cookieValue = "true";

        httpResponse.addCookie(cookieName, cookieValue);
    }

    @Test
    @DisplayName("response200HTML 메서드 호출 시 상태코드는 302가 된다")
    void response200HtmlStatusCodeTest() {
        final StatusCode statusCode = StatusCode.OK;
        final int expected = statusCode.getCode();
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.response200HTML(body);
        int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response200HTML 메서드 호출 시 정상적인 Content-Type 헤더가 추가된다")
    void response200HtmlContentTypeTest() {
        final HeaderFieldName contentType = HeaderFieldName.CONTENT_TYPE;
        final String expected = "text/html;charset=utf-8";
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.response200HTML(body);
        String result = httpResponse.getHeader(contentType);

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

        httpResponse.response200HTML(body);
        String result = httpResponse.getHeader(contentLength);

        assertThat(result).isEqualTo(expected);
    }


    @Test
    @DisplayName("response200CSS 메서드 호출 시 상태코드는 302가 된다")
    void response200CssStatusCodeTest() {
        final StatusCode statusCode = StatusCode.OK;
        final int expected = statusCode.getCode();
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.response200CSS(body);
        int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response200CSS 메서드 호출 시 정상적인 Content-Type 헤더가 추가된다")
    void response200CssContentTypeTest() {
        final HeaderFieldName contentType = HeaderFieldName.CONTENT_TYPE;
        final String expected = "text/css;charset=utf-8";
        final byte[] body = new byte[0];
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.response200CSS(body);
        String result = httpResponse.getHeader(contentType);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response200CSS 메서드 호출 시 정상적인 Content-Length 헤더가 추가된다")
    void response200CssContentLengthTest() {
        final HeaderFieldName contentLength = HeaderFieldName.CONTENT_LENGTH;
        String css = "body {\n" +
                "  background-color:#e0e0e0;\n" +
                "}";
        final byte[] body = css.getBytes();
        final String expected = String.valueOf(body.length);
        final HttpResponse httpResponse = new HttpResponse();

        httpResponse.response200CSS(body);
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

        httpResponse.response302(locationUrl);
        int result = httpResponse.getStatusCode();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("response302 메서드 호출 시 입력받은 url을 가지는 Location 헤더가 추가된다")
    void response302LocationTest() {
        final HttpResponse httpResponse = new HttpResponse();
        final String locationUrl = "http://www.google.com";

        httpResponse.response302(locationUrl);
        String result = httpResponse.getHeader(HeaderFieldName.LOCATION);

        assertThat(result).isEqualTo(locationUrl);
    }

}