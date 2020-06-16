package http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

// TODO 목킹안해서 깨지는 부분 수정
class HttpResponseTest {
    private HttpResponse sut;

    @Test
    void css_HttpResponse_success() {
        // given
        String filePath = "/css/test.css";
        HttpResponse httpResponse = sut.from(filePath, HttpStatus.OK);

        // when
        String rawHttpResponse = new String(httpResponse.makeHttpResponseBytes());


        // then
        assertThat(rawHttpResponse.contains("text/css")).isTrue();
    }

    @Test
    void html_HttpResponse_success() {
        // given
        String filePath = "/css/test.html";
        HttpResponse httpResponse = sut.from(filePath, HttpStatus.OK);

        // when
        String rawHttpResponse = new String(httpResponse.makeHttpResponseBytes());


        // then
        assertThat(rawHttpResponse.contains("text/html")).isTrue();

    }

}