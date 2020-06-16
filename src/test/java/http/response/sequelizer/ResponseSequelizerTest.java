package http.response.sequelizer;

import http.common.HeaderField;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseSequelizerTest {

    @Test
    @DisplayName("httpResponse에 statusCode가 존재할 경우 ResponseLine 문자열이 정상적으로 나온다")
    void responseLine() {
        final HttpResponse httpResponse = new HttpResponse();
        final String expected = "HTTP/1.1 200 Ok\r\n";

        final String result = ResponseSequelizer.RESPONSE_LINE.sequelize(httpResponse);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("httpResponse에 쿠키가 존재할 경우 쿠키 문자열이 정상적으로 나온다")
    public void cookie() {
        final HttpResponse httpResponse = new HttpResponse();
        httpResponse.addCookie("a", "1");
        httpResponse.addCookie("b", "2");
        final String expected1 = "Set-Cookie: a=1";
        final String expected2 = "Set-Cookie: b=2";

        final String result = ResponseSequelizer.COOKIE.sequelize(httpResponse);
        assertThat(result).contains(expected1, expected2);
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