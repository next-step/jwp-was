package http.response.sequelizer;

import http.common.ContentType;
import http.common.HeaderField;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.StatusCodeNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ResponseSequelizerTest {

    @Test
    @DisplayName("httpResponse에 statusCode가 존재할 경우 ResponseLine 문자열이 정상적으로 나온다")
    void responseLine() {
        final HttpResponse httpResponse = new HttpResponse();
        httpResponse.response200(ContentType.TEXT_HTML_UTF_8, new byte[0]);
        final String expected = "HTTP/1.1 200 Ok\r\n";

        final String result = ResponseSequelizer.RESPONSE_LINE.sequelize(httpResponse);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("httpResponse에 statusCode가 존재하지 않을 경우 에러를 반환한다")
    void emptyStatusCodeResponseLine() {
        final HttpResponse httpResponse = new HttpResponse();

        final Throwable thrown = catchThrowable(() -> ResponseSequelizer.RESPONSE_LINE.sequelize(httpResponse));

        assertThat(thrown)
                .isInstanceOf(StatusCodeNotFoundException.class)
                .hasMessageContaining(ErrorMessage.STATUS_CODE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("httpResponse에 쿠키가 존재할 경우 쿠키 문자열이 정상적으로 나온다")
    public void cookie() {
        final HttpResponse httpResponse = new HttpResponse();
        httpResponse.addCookie("a", "1");
        httpResponse.addCookie("b", "2");
        final String expected = "Set-Cookie: a=1; b=2\r\n";

        final String result = ResponseSequelizer.COOKIE.sequelize(httpResponse);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("httpResponse에 헤더가 존재할 경우 헤더 문자열이 정상적으로 나온다")
    public void header() {
        final HttpResponse httpResponse = new HttpResponse();
        final ResponseHeader header = httpResponse.getHeader();
        header.addHeader(new HeaderField("a", "1"));
        header.addHeader(new HeaderField("b", "2"));
        final String expected = "a: 1\r\n" +
                                "b: 2\r\n" +
                                "\r\n";

        final String result = ResponseSequelizer.HEADER.sequelize(httpResponse);

        assertThat(result).isEqualTo(expected);
    }

}