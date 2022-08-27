package http.httpresponse;


import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseTest {


    @Test
    @DisplayName("생성 잘 되는지 확인")
    void create() {
        HttpResponse httpResponse = new HttpResponse(
                new StatusLine(HttpStatusCode.OK),
                new ResponseHeader(Collections.singletonMap(HttpHeaders.SET_COOKIE, "logined=true; Path=/"))
        );

        assertAll(
                () -> assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK),
                () -> assertThat(httpResponse.getResponseHeader()).isEqualTo(new ResponseHeader(Collections.singletonMap(HttpHeaders.SET_COOKIE, "logined=true; Path=/")))
        );
    }

    @Test
    @DisplayName("리다이렉트 헤더가 잘 적용되는지 확인")
    void sendRedirect() {
        HttpResponse httpResponse = HttpResponse.sendRedirect("/index.html");


        assertAll(
                () -> assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND),
                () -> assertThat(httpResponse.getResponseHeader()).isEqualTo(new ResponseHeader(Collections.singletonMap(HttpHeaders.LOCATION, "/index.html")))
        );
    }

    @Test
    @DisplayName("notFound Status가 정상인지 확인")
    void notFound() {
        HttpResponse httpResponse = HttpResponse.notFound();

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.NOT_FOUND);
    }

    @Test
    @DisplayName("internalServerError Status가 정상인지 확인")
    void internalServerError() {
        HttpResponse httpResponse = HttpResponse.internalServerError();

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

}